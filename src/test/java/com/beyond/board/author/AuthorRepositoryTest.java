package com.beyond.board.author;

import com.beyond.board.author.domain.Author;
import com.beyond.board.author.domain.Role;
import com.beyond.board.author.repository.AuthorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional  //rollback처리를 위해 트랜잭셔널 사용
public class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;


    @Test   //test할 거에 붙이기
    public void authorSaveTest(){
        //테스트 원리 : 저장->재조회->저장할 때 만든 객체와 비교
        //준비단계(prepare, given)
        Author author = Author.builder()
                .name("hongkk")
                .email("hongkk@namver.com")
                .password("1234")
                .role(Role.ADMIN)
                .build();

        //실행단계 (execute, when)
        authorRepository.save(author);
        Author savedAuthor = authorRepository.findByEmail("hongkk@namver.com").orElse(null);


        //검증단계 (then)
        Assertions.assertEquals(author.getEmail(), savedAuthor.getEmail());

    }
}
