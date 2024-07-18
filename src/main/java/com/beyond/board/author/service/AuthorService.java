package com.beyond.board.author.service;

import com.beyond.board.author.domain.Author;
import com.beyond.board.author.dto.AuthorDetResDto;
import com.beyond.board.author.dto.AuthorSaveReqDto;
import com.beyond.board.author.dto.AuthorListResDto;
import com.beyond.board.author.repository.AuthorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
//조회 작업시 readOnly 설정하면 성능 향상
//다만 저장 작업 시 @transcational 필요
@Transactional(readOnly = true)
public class AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository){
        this.authorRepository = authorRepository;
    }

    @Transactional
    public Author createAuthor(AuthorSaveReqDto authorReqDto){
        if(authorRepository.findByEmail(authorReqDto.getEmail()).isPresent()){
            throw new IllegalArgumentException("이미 존재하는 email 입니다.");
        }
        Author author = authorReqDto.toEntity();
        Author savedAuthor = authorRepository.save(author);
        return savedAuthor;

//

    }

    public List<AuthorListResDto> authorList() {
        List<Author> authorList = authorRepository.findAll();
        List<AuthorListResDto> authorListResDtoList = new ArrayList<>();
        for(Author author : authorList){
            authorListResDtoList.add(author.fromEntity());
        }
        return authorListResDtoList;
    }

    public AuthorDetResDto showAuthorDetail(Long id) {
        Author author = authorRepository.findById(id).orElseThrow(()->new EntityNotFoundException("member isn;'t found"));
        AuthorDetResDto authorDetResDto = new AuthorDetResDto();

        //Author에 있던 fromEntity를 바꿈
        return authorDetResDto.fromEntity(author);
    }


    public Author authorFindByEmail(String email){
        Author author = authorRepository.findByEmail(email).orElseThrow(()->new EntityNotFoundException("해당 email의 사용자는 없습니다."));
        return author;
    }
}
