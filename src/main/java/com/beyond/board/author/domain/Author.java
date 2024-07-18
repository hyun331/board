package com.beyond.board.author.domain;

import com.beyond.board.author.dto.AuthorDetResDto;
import com.beyond.board.author.dto.AuthorListResDto;
import com.beyond.board.common.BaseEntity;
import com.beyond.board.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Entity
//Builder 어노테이션을 통해 매개변수의 순서, 매개변수의 개수 등을 유연하게 세팅하여 생성자로서 활용
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Author extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String name;

    @Column(length = 30, nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    //enum을 int가 아닌 varchar로 만들기
    @Enumerated(EnumType.STRING)
    private Role role;

    //Post의 author 변수와 연결됨
    @OneToMany(mappedBy = "author")
    private List<Post> posts;

    public Author(String name, String email, String password, Role role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public AuthorListResDto fromEntity() {
        AuthorListResDto authorListResDto = AuthorListResDto.builder()
                .id(this.id)
                .name(this.name)
                .email(this.email)
                .build();
        return authorListResDto;
    }


}
