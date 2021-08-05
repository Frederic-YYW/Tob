package com.hw.tobcore.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Description:
 * @Author:Frederic-YYW
 * @Email:yuanyangwen@iristar.com.cn
 * @Date 2020/1/8 16:18
 */
public class TokenUtil {

    public static String getAdminid(String token) {
        if (StringUtils.isBlank(token)) {
            return null;
        }
        String jwtStr = token.split(" ")[1];
        Map<String, Object> jwtMap = JwtUtil.verifyToken(jwtStr);
        String userId = jwtMap.get("userid").toString();
        return userId;
    }
    public static String getAdminid(HttpServletRequest request) {
        String token = request.getParameter("token");
        if (StringUtils.isBlank(token)) {
            return null;
        }
        String jwtStr = token.split(" ")[1];
        Map<String, Object> jwtMap = JwtUtil.verifyToken(jwtStr);
        String userId = jwtMap.get("userid").toString();
        return userId;
    }
}
