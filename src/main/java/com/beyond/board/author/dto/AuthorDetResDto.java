package com.beyond.board.author.dto;

import com.beyond.board.author.domain.Author;
import com.beyond.board.author.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class AuthorDetResDto {
    private Long id;
    private String name;
    private String email;
    private String password;
    private Role role;
    private String createdTime;
    private String updatedTime;
    private int postCounts;

    //원래는 이건 Author에 있었는데?
    //Author에 fromEntity가 너무 많아서 그런듯
    public AuthorDetResDto fromEntity(Author author){
        return AuthorDetResDto.builder()
                .id(author.getId())
                .name(author.getName())
                .email(author.getEmail())
                .role(author.getRole())
                .password(author.getPassword())
                .postCounts(author.getPosts().size())
                .createdTime(author.getCreatedTime().toString())
                .build();
    }
}
