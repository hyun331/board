package com.beyond.board.post.domain;

import com.beyond.board.author.domain.Author;
import com.beyond.board.common.BaseEntity;
import com.beyond.board.post.dto.PostDetResDto;
import com.beyond.board.post.dto.PostListResDto;
import com.beyond.board.post.dto.PostUpdateDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String title;

    @Column(length = 3000)
    private String contents;

    //연관관계의 주인은 fk가 있는 post
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id") //외래키로 지정할거면 작성해야함
    private Author author;

    private String appointment;
    private LocalDateTime appointmentTime;



    public PostListResDto listFromEntity(){
        return PostListResDto
                .builder()
                .id(this.id)
                .title(this.title)
                .author_email(this.author.getEmail())
                .build();
    }

    public PostDetResDto detFromEntity(){
        return PostDetResDto.builder()
                .id(this.id)
                .title(this.title)
                .contents(this.contents)
                .author_email(this.getAuthor().getEmail())
                .createdTime(this.getCreatedTime())
                .updatedTime(this.getUpdatedTime()).build();

    }


    public void updatePost(PostUpdateDto dto) {

        this.title = dto.getTitle();
        this.contents = dto.getContents();
    }

    public void changeAppointment(String yn){
        this.appointment = yn;
    }
}
