package com.beyond.board.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity  //spring security설정을 customizing하기 위해
@EnableGlobalMethodSecurity(prePostEnabled = true) //pre: 사전에 인증검사, post:사후 임증검사
public class SecurityConfig {

    //SecurityFilterChain, HttpSecurity 상속관계
    @Bean
    public SecurityFilterChain myFilter(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                //crsf공격에 대한 설정은 하지 않겠다는 의미. 방어를 하지 않겠다
                .csrf().disable()
                .


                .build();
    }
}
