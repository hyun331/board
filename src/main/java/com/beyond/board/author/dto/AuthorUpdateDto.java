package com.beyond.board.author.dto;

import com.beyond.board.author.domain.Author;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorUpdateDto {
    private String name;
    private String password;


}
