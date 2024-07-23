package com.beyond.board.common;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//slf4j 어노테이션 또는 Logger 직접 선언
//로거 직접 선언
@RestController
@Slf4j
public class LogController {
//    private static final Logger logger = LoggerFactory.getLogger(LogController.class);

    @GetMapping("/log/test1")
    public String logTest1(){
        //기존의 로그 방식 : System.out.println()
        //문제점 1 : 성능이 좋지 않음, 로그 분류작업 불가
        System.out.println("println 로그입니다");
//        logger.debug("debug logger입니다.");
//        logger.info("info logger입니다.");
//        logger.error("error logger입니다.");
        log.info("slf4j 어노테이션을 사용하는 방법");

        return "ok";
    }
}
