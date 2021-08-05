package com.hw.tobcore.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iristar.center.entity.base.Admin;
import com.iristar.center.entity.base.ResObject;
import com.iristar.center.enums.ResultEnum;
import com.iristar.center.service.AdminService;
import com.iristar.center.util.ConfigHelper;
import com.iristar.center.util.Constants;
import com.iristar.center.util.JwtUtil;
import com.iristar.center.util.StringUtils;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;


/**
 * 过滤器
 */
//@Component
public class CheckLoginFilter implements Filter {

    private Logger log = LoggerFactory.getLogger(this.getClass());
    //排除需要过滤的请求
    private String excludeURL = "";
    private String default_excludeURL = "login,upload,register";
    private String[] url_array = null;
    private String forward = "system.html";
    @Autowired
    AdminService adminService;

    /**
     * 初始化
     *
     * @param filterConfig
     * @throws ServletException
     */
    public void init(FilterConfig filterConfig) throws ServletException {
        //设置过滤请求路径
        String url = StringUtils.trim(ConfigHelper.getString("filter.exclude.url"));
        if (StringUtils.isBlank(url)) {
            excludeURL = default_excludeURL;
        } else {
            excludeURL = url;
        }
        url_array = excludeURL.split(",");
        log.info("CheckLoginFilter init 初始化.......");
    }

    /**
     * 拦截实现
     *
     * @param servletRequest
     * @param servletResponse
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        ServletRequest requestWrapper = null;
        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)) {
            //ie8 support
            if ("POST".equals(request.getMethod().toUpperCase())) {

                boolean multipartContent = ServletFileUpload.isMultipartContent(request);
                if (!multipartContent) {
                    requestWrapper = new RequestWrapper(request);
                    if (requestWrapper != null) {
                        String param = this.getBodyString(requestWrapper.getReader());
                        JSONObject jsonObject = JSON.parseObject(param);
                        token = jsonObject.getString("token");
                    }
                }
            } else {
                token = request.getParameter("token");
            }
            log.error("token={}", token == null ? "token is empty" : token);
        }

     //   response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, token");
//        response.setHeader("Access-Control-Allow-Headers", "*");

        String url = request.getRequestURI();

        System.out.println("token::" + token + " url::" + url);
        log.debug("CheckLoginFilter method：" + request.getMethod() + ", url: " + url);
        String lastUrl = StringUtils.substringAfterLast(url, "/");

        if (request.getMethod().equalsIgnoreCase("options")) {
            response.setStatus(204);
            PrintWriter out = response.getWriter();
            out.close();
            return;
        }

        if (StringUtils.isNotBlank(token) && !token.equals("undefined")) {
            try {
                String jwtStr = token.split(" ")[1];

                Map<String, Object> jwtMap = JwtUtil.verifyToken(jwtStr);
                String userId = jwtMap.get("userid").toString();
                Date expireAt = (Date) jwtMap.get("expiresAt");

                if (expireAt.compareTo(new Date()) < 0) {
                    log.warn("token expired: " + expireAt);
                    errorResp(response, "token expired", ResultEnum.TOKEN_EXPIRED.getCode());
                }

                Admin admin = adminService.findByPrimaryKey(userId);
                if (admin != null && admin.isEnable()) {
                    session.setAttribute(Constants.User.COOKIE_USER, admin);
                    Admin admin1 = new Admin();
                    admin1.setId(admin.getId());
                    admin1.setOperateAt(new Date());
                    admin1.setEnable(true);
                    adminService.updateByPrimaryKey(admin1);
                    chain.doFilter(requestWrapper != null ? requestWrapper : request, servletResponse);
                } else {
                    if (admin == null) {
                        log.warn("No user found");
                        errorResp(response, "No user found", ResultEnum.USER_NOT_FOUND.getCode());
                    } else if (admin != null && admin.getStatus() == Constants.UserStatus.FORBID) {
                        log.warn("User is forbid");
                        errorResp(response, "User is forbid", ResultEnum.USER_FORBID.getCode());
                    } else {
                        log.warn("unknown error");
                        errorResp(response, "unknown error", ResultEnum.USER_FORBID.getCode());
                    }
                }
            } catch (Exception e) {
                log.info(e.getMessage());
            }

        } else {
            if (!StringUtils.isBlank(lastUrl) && checkNoneLoginUrl(lastUrl)) {
                chain.doFilter(requestWrapper != null ? requestWrapper : request, servletResponse);
            } else {
                errorResp(response, "you are not login, please login first", ResultEnum.NOT_LOGIN.getCode());
                log.info("用户未登陆，请登陆!");
            }
        }

    }

    private void errorResp(HttpServletResponse response, String message, Integer code) throws IOException {
        ResObject jsonView = new ResObject();
        PrintWriter out = response.getWriter();
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            jsonView.failPack(message, code);
            out.append(JSON.toJSONString(jsonView));
        } catch (Exception e) {
            log.info(e.getMessage());
        } finally {
            out.close();
        }
    }

    /**
     * 判断当前URL是否需要拦截
     *
     * @param url
     * @return
     */
    private boolean checkNoneLoginUrl(String url) {
        boolean flag = false;
        for (String location : url_array) {
            if (location.equals(url.trim())) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     * 注销filter
     */
    public void destroy() {
        log.error("CheckLoginFilter destroy......");
    }

    public String getBodyString(BufferedReader br) {
        String inputLine;
        String str = "";
        try {
            while ((inputLine = br.readLine()) != null) {
                str += inputLine;
            }
            br.close();
        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }
        return str;
    }
}
