package com.beyond.board.author;

import com.beyond.board.author.domain.Author;
import com.beyond.board.author.domain.Role;
import com.beyond.board.author.dto.AuthorSaveReqDto;
import com.beyond.board.author.dto.AuthorUpdateDto;
import com.beyond.board.author.repository.AuthorRepository;
import com.beyond.board.author.service.AuthorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@SpringBootTest
@Transactional
//@Rollback
public class AuthorServiceTest {

    @Autowired
    private AuthorService authorService;
    @Autowired
    private AuthorRepository authorRepository;

    //저장 및 detail 조회

    @Test
    void saveAndFind(){
        AuthorSaveReqDto authorDto = new AuthorSaveReqDto("bbb", "bbb2@naver.com","12341234", Role.USER);
        Author author = authorService.createAuthor(authorDto);
        Author authorDetail = authorService.authorFindByEmail("bbb2@naver.com");

        Assertions.assertEquals(authorDetail.getEmail(), authorDto.getEmail());
    }

    //update 검증
    //객체 create
    //수정작업 name, password 바꾼 후 재조회해서 name과 password가 맞는지 각각 검증
    @Test
    void updateAndFind(){
        AuthorSaveReqDto authorDto = new AuthorSaveReqDto("zzz", "zzz@naver.com","12341234", Role.USER);
        Author author = authorService.createAuthor(authorDto);

        AuthorUpdateDto authorUpdateDto = new AuthorUpdateDto("zzznewName", "newPassword!!@");
        authorService.update(author.getId(), authorUpdateDto);

        //재조회
        Author authorDetail = authorRepository.findById(author.getId()).orElseThrow(()->new EntityNotFoundException());
        Assertions.assertEquals(authorDto.getName(),  authorDetail.getName());
        Assertions.assertEquals(authorDto.getPassword(), authorDetail.getPassword());


    }



    //findAll 검증
    @Test
    public void findAllTest(){
        List<Author> beforeAuthorList = authorRepository.findAll();
        //3개 author 저장
        authorService.createAuthor(new AuthorSaveReqDto("zzz1", "zzz1@naver.com","12341234", Role.USER));
        authorService.createAuthor(new AuthorSaveReqDto("zzz2", "zzz2@naver.com","12341234", Role.USER));
        authorService.createAuthor(new AuthorSaveReqDto("zzz3", "zzz3@naver.com","12341234", Role.USER));


        //authorList 조회한 후 저장한개수와 저장된 개수 비교
        List<Author> authorList = authorRepository.findAll();
        Assertions.assertEquals(3+beforeAuthorList.size(), authorList.size());
    }

}
