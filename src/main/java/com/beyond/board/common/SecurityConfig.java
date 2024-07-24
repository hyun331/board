package com.beyond.board.common;

import com.beyond.board.author.service.LoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity  //spring security설정을 customizing하기 위해
@EnableGlobalMethodSecurity(prePostEnabled = true) //pre: 사전에 인증검사, post:사후 임증검사
public class SecurityConfig {

    //SecurityFilterChain, HttpSecurity 상속관계
    @Bean
    //사용자가 서비스에 접근하려면 우선 filter를 거쳐야함.
    //HttpSecurity의 구현체를 우리가 만드는 것
    public SecurityFilterChain myFilter(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                //crsf공격에 대한 설정은 하지 않겠다는 의미. 방어를 하지 않겠다. 근데 원래 해야함
                .csrf().disable()
                //springsecurity 의존성을 추가하면서 모든 화면에 대해 인증하도록 변함 ->
                .authorizeRequests()
                    .antMatchers("/", "/author/register", "/author/login-screen")  //인증 제외
                    .permitAll()
                    //그 외 요청은 모두 인증
                    .anyRequest().authenticated()
                .and()
                //만약에 세션로그인이 아니라 토큰 로그인일 경우
//                .sessionManagement().sessionCreationPolicy((SessionCreationPolicy.STATELESS))
                .formLogin()
                    .loginPage("/author/login-screen")  //스프링에서 제공하는 로그인 페이지가 아닌 우리가 만든 로그인 페이지를 사용하겠다
                        .loginProcessingUrl("/doLogin")   //doLogin 메서드는 스프링에서 제공하는 로그인 기능
                            .usernameParameter("email") //다만 doLogin에 넘겨줄 파라미터는 email, password 속성명은 별도 지정
                            .passwordParameter("password")
                .successHandler(new LoginSuccessHandler())
                .and()
                    .logout()
                    .logoutUrl("/doLogout") //스프링 시큐리티에서 제공하는 로그아웃 기능
                .and()
                .build();
    }
}
