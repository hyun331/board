package com.beyond.board.author.dto;

import com.beyond.board.author.domain.Author;
import com.beyond.board.author.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorSaveReqDto {
    private String name;
    private String email;
    private String password;
    //사용자가 String으로 요청해도 Role클래스로 자동형변환
    private Role role;



    public Author toEntity(String encodedPassword) {
        Author author = Author.builder()
                .password(encodedPassword)
                .name(this.name)
                .email(this.email)
                .role(this.role)
                .posts(new ArrayList<>())
                .build();

        return author;
    }
}
