package com.beyond.board.post.dto;

import com.beyond.board.author.domain.Author;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostListResDto {

    private Long id;
    private String title;
    //author객체 그 자체를 리턴하게 되면 Author안에 Post가 재참조되어 순환참조 이슈 발생
    private String author_email;
}
