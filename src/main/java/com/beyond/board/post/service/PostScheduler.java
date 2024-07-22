//package com.beyond.board.post.service;
//
//import com.beyond.board.post.domain.Post;
//import com.beyond.board.post.repository.PostRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDateTime;
//
//@Component  //싱글톤 객체로 등록
//public class PostScheduler {
//    private final PostRepository postRepository;
//
//    @Autowired
//    public PostScheduler(PostRepository postRepository){
//        this.postRepository = postRepository;
//    }
//
//
//    //아래 스케쥴의 cron부는 각 자리마다 초 분 시간 일 월 요일
////예) * * * * * * : 매월 매요일 매일 매시간 매분 매초마다
////예) 0 0 * * * * : 매일 0분 0초에 -> 1시간에 한번
////예) 0 0 11 * * * : 매일 11시에
////예) 0 0/1 * * * * : 매일 매시 1분마다
////예) 0 1 * * * * : 매일 매시 1에
//    @Scheduled(cron = "0 0/1 * * * *")
//    @Transactional
//    public void postSchedule(){
//        System.out.println("==예약 글쓰기 스케쥴러 시작==");
//        Page<Post> posts = postRepository.findByAppointment(Pageable.unpaged(), "Y");   //현재 페이징 처리  필요없음
//        for(Post p : posts){
//            if(p.getAppointmentTime().isBefore(LocalDateTime.now())){   //예약시간이 현재 시간보다 지났으면.
//                p.changeAppointment("N");
//                System.out.println("예약 시간 지남");
//            }
//        }
//    }
//}
