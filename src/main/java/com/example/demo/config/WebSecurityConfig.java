package com.example.demo.config;

import com.example.demo.security.JwtAuthenticationFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.filter.CorsFilter;

@Slf4j
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        //http 시큐리티 빌더
        http.cors() //WebMvcConfig에서 이미 설정했으므로 기본 cors 설정.
                .and()
                .csrf() //csrf는 현재 사용하지 않으므로 disable
                    .disable()
                .httpBasic() //token을 사용하므로 basic 인증 disable
                    .disable()
                .sessionManagement() //session 기반이 아님을 선언
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests() // /와 /auth/** 경로는 인증 안해도됨
                    .antMatchers("/", "/auth/**").permitAll()
                .anyRequest() // /과 /auth/** 이외의 모든 경로는 인증 해야됨
                    .authenticated();

        //filter 등록.
        //매 요청마다 jwtAuthenticationFilter을 실행한다.
        http.addFilterAfter(jwtAuthenticationFilter, CorsFilter.class);
    }
}
// HTTPSecurity는 시큐리티 설정을 위한 오브젝트이다.
// 이 오브젝트는 빌더를 제공하는데 빌더를 이용해 cors, csrf, httpbasic, session, authorizeRequest 등 다양한 설정을 할 수 있다.
// HttpSecurity를 이용해 시큐리티 관련 설정을 하는 것이다.
// addFilterAfter() 메서드를 실행하는 것을 알 수 있다. 이것도 jwtAuthenticationFilter를 CorsFilter 이후에 실행하라고 설정 하는 것이다.

