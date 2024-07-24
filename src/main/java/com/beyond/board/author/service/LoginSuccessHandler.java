package com.beyond.board.author.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//로그인 성공하면 여기로 와서 onAuthenticationSuccess()호출
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //사용자의 요청으로부터 session을 가져와서
        HttpSession httpSession = request.getSession();

        //방법 1. authentication 인증객체에서 email정보 추출
        //여기의 getName은 username과 같음 -> 우리는 email을 username으로 사용
        //session에 email값을 추가
        httpSession.setAttribute("email", authentication.getName());



        //방법 2. SecurityContextHolder 객체에서 authentication객체를 꺼낸뒤 email 정보 추추출
        httpSession.setAttribute("email2", SecurityContextHolder.getContext().getAuthentication().getName());
        //로그인 성공 후 home으로 가도록
        response.sendRedirect("/");
    }
}
