package com.hw.tobcore.filter;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Description:跨域请求配置
 * @Author:Frederic-YYW
 * @Email:yuanyangwen@iristar.com.cn
 * @Date 2020/7/13 10:22
 */
@Configuration
public class CorsConfig extends WebMvcConfigurerAdapter {


    @Override

    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/**")

                .allowedOrigins("*")
                .allowedMethods("*") //允许任何方法（post、get等）
                .allowedHeaders("*") //允许任何请求头
                .allowCredentials(true)//带上cookie信息
                .allowedMethods("GET", "POST", "DELETE", "PUT")//允许任何方法
                .maxAge(3600); //maxAge(3600)表明在3600秒内，不需要再发送预检验请求，可以缓存该结果

    }
}

