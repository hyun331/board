package com.beyond.board.author.domain;

import com.beyond.board.author.dto.AuthorDetResDto;
import com.beyond.board.author.dto.AuthorResDto;
import com.beyond.board.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Author extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(nullable = false)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    public Author(String name, String email, String password, Role role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public AuthorResDto fromEntity() {
        return new AuthorResDto(this.id, this.name, this.email, this.role);
    }

    public AuthorDetResDto detFromEntity() {
        LocalDateTime createdTime = this.getCreatedTime();
        String createdTimeStr = createdTime.getYear()+"년"+createdTime.getMonthValue()+"월"+createdTime.getDayOfMonth()+"일";
        LocalDateTime updated = this.getUpdatedTime();
        String updateTimeStr = updated.getYear()+"년"+createdTime.getMonthValue()+"월"+createdTime.getDayOfMonth()+"일";
        return new AuthorDetResDto(this.id, this.name, this.email, this.role, createdTimeStr, updateTimeStr);
    }
}
