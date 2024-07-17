package com.beyond.board.author.dto;

import com.beyond.board.author.domain.Author;
import com.beyond.board.author.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorReqDto {
    private String name;
    private String email;
    private String password;
    private Role role;

    public Author toEntity() {
        return new Author(this.name, this.email, this.password, this.role);
    }
}
