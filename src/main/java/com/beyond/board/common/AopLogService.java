//package com.beyond.board.common;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.node.ObjectNode;
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.*;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.Map;
//
////Aspect : AOP 코드임을 명시
//@Aspect
//@Slf4j
//@Component
//public class AopLogService {
//    //aop의 대상(공토오하의 대상)이 되는 Controller, service등의 위치를 명시
//    @Pointcut("within(@org.springframework.stereotype.Controller *)")   //모든 컨트롤러 어노테이션이 붙은것을 대상으로함
//    public void controllerPointCut(){
//
//    }
//
//    //around 방식 -> controller와 누구가 걸쳐져있는
//    @Around("controllerPointCut()")
//    //jointPoint는 사용자가 실행하려고 하는 코드를 의미하고 위에서 정의한 Pointcut을 의미 -> 낚아챈 컨트롤러들
//    public Object controllerLogger(ProceedingJoinPoint joinPoint){
//        Object result = null;
//        log.info("aop start");
//        log.info("Method명 : "+joinPoint.getSignature().getName());
//
//        //직접 httpservletRequest 객체를 꺼내는 법 -> 사용자 요청 정보가 들어있음
//        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = attributes.getRequest();
//        Map<String, String[]> parameterMap = request.getParameterMap();
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        ObjectNode objectNode = objectMapper.valueToTree(parameterMap);
//
//        log.info("user inputs : "+objectNode);
//        log.info("http method : "+request.getMethod());
//        try {
//            result= joinPoint.proceed();   //사용자가 실행하고자 하는 코드 실행부.
//        } catch (Throwable e) {
//            throw new RuntimeException(e);
//        }
//        log.info("aop end");
//
//        return result;
//    }
//
//
//    //before, after annotation 방식
////    @Before("controllerPointCut()")
////    public void beforeController(JoinPoint joinPoint){
////        log.info("aop start");
////        log.info("Method명 : "+joinPoint.getSignature().getName());
////
////        //직접 httpservletRequest 객체를 꺼내는 법 -> 사용자 요청 정보가 들어있음
////        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
////        HttpServletRequest request = attributes.getRequest();
////        log.info("http method : "+request.getMethod());
////    }
////
////
////
////    @After("controllerPointCut()")
////    public void afterController(){
////        log.info("aop end");
////    }
//
//}
