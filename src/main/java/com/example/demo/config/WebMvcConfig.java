package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final long MAX_AGE_SECS = 3000;

    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")

                .allowedOrigins("http://localhost:3000")
//                .allowedMethods("*")
                .allowedMethods("GET","POST","PUT","PATCH","DELETE","OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(MAX_AGE_SECS);
    }
}

//모든경로 (/**)에 대해 Origin이 http:localhost:3000 프론트엔드 요청에 대해
//GET, POST, PUT, PATCH, DELETE, OPTION 메서드를 허용한다. 또한 모든 헤더와 인증에 관한 정보(Credential)도 허용한다.
