package com.beyond.board.author.dto;

import com.beyond.board.author.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDetResDto {
    private Long id;
    private String name;
    private String email;
    private Role role;
    private String createdTime;
    private String updatedTime;
}
