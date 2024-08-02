package com.classicmodels.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {

    @Value("${CONTROLLERS_CROSS_ORIGIN}")
    private String crossOrigin;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //registry.addMapping(crossOrigin);
        registry.addMapping("/employees").allowedOrigins(crossOrigin);
        registry.addMapping("/employees/idname").allowedOrigins(crossOrigin);
        registry.addMapping("/offices/idcity").allowedOrigins(crossOrigin);
        registry.addMapping("offices/id").allowedOrigins(crossOrigin);
        registry.addMapping("/employees/employeeslist").allowedOrigins(crossOrigin);
    }

}
