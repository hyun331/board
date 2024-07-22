package com.beyond.board.post.service;

import com.beyond.board.post.domain.Post;
import com.beyond.board.post.repository.PostRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

//작업 목록 정의
@Configuration  //@Bean 있으면 항상 붙이기
public class PostJobConfiguration {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private PostRepository postRepository;

    //import시 배치코어
    public Job excuteJob(){
        return jobBuilderFactory.get("executeJob")
                .start(firstStep())
                .next(secondStep())
                .build();
    }

    @Bean
    public Step firstStep(){
        return stepBuilderFactory.get("firstStep")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("==batch task1 start==");
                    Page<Post> posts = postRepository.findByAppointment(Pageable.unpaged(), "Y");   //현재 페이징 처리  필요없음
                    for(Post p : posts){
                        if(p.getAppointmentTime().isBefore(LocalDateTime.now())){   //예약시간이 현재 시간보다 지났으면.
                            p.changeAppointment("N");
                        }
                    }
                    System.out.println("==batch task1 finish==");

                    return RepeatStatus.FINISHED;

                }).build();
    }
    @Bean
    public Step secondStep(){
        return stepBuilderFactory.get("firstStep")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("==batch task2 start==");
                    System.out.println("==batch task2 finish==");
                    return RepeatStatus.FINISHED;
                }).build();
    }
}
