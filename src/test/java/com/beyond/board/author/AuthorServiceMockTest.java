package com.beyond.board.author;

import com.beyond.board.author.domain.Author;
import com.beyond.board.author.domain.Role;
import com.beyond.board.author.dto.AuthorDetResDto;
import com.beyond.board.author.dto.AuthorSaveReqDto;
import com.beyond.board.author.repository.AuthorRepository;
import com.beyond.board.author.service.AuthorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

//service를 테스트할 때 repository 변수가 발생할 수 있음.
//그래서 가짜 repository를 만들어서 일관된 응답을 받을 수 있음
@SpringBootTest
@Transactional
public class AuthorServiceMockTest {
    @Autowired
    private AuthorService authorService;
//    @Autowired
//    private AuthorRepository authorRepository;

    //가짜 객체를 만드는 작업을 목킹이라 한다.
    @MockBean
    @Autowired
    private AuthorRepository authorRepository;

    @Test
    public void findAuthorDetailTest(){
        AuthorSaveReqDto authorDto = new AuthorSaveReqDto("bbb", "bbb2@naver.com","12341234", Role.USER);
        Author author1 = authorService.createAuthor(authorDto);
        Author myAuthor = Author.builder().id(1L).name("test").email("test@naver.com").build();
        AuthorDetResDto authorDetResDto = authorService.showAuthorDetail(author1.getId());
//        Author savedAuthor = authorRepository.findById(author1.getId()).orElseThrow(()->new EntityNotFoundException("not found"));

        //Mockito.when - ~하면
        //thenReturn - ~해준다
        //author1.getId()로 조회하면 직접 정의한 객체를 리턴해준다
        Mockito.when(authorRepository.findById(author1.getId())).thenReturn(Optional.of(author1));
        Author savedAuthor = authorRepository.findById(author1.getId()).orElseThrow(()->new EntityNotFoundException("not found"));



        Assertions.assertEquals(authorDetResDto.getEmail(), savedAuthor.getEmail());
    }
}
