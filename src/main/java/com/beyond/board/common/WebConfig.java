package com.beyond.board.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebConfig {

    @Bean
    public PasswordEncoder makePassword(){
        // 스프링 시큐리티에서 제공하는 암호화 기능
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
