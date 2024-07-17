package com.beyond.board.author.service;

import com.beyond.board.author.domain.Author;
import com.beyond.board.author.dto.AuthorDetResDto;
import com.beyond.board.author.dto.AuthorReqDto;
import com.beyond.board.author.dto.AuthorResDto;
import com.beyond.board.author.repository.AuthorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository){
        this.authorRepository = authorRepository;
    }

    public void createAuthor(AuthorReqDto authorReqDto){
        if(authorRepository.findByEmail(authorReqDto.getEmail()).isPresent()){
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }
        Author newAuthor = authorReqDto.toEntity();

        authorRepository.save(newAuthor);

    }

    public List<AuthorResDto> showAuthorList() {
        List<Author> authorList = authorRepository.findAll();
        List<AuthorResDto> authorResDtoList = new ArrayList<>();
        for(Author author : authorList){
            authorResDtoList.add(author.fromEntity());
        }
        return authorResDtoList;
    }

    public AuthorDetResDto showAuthorDetail(Long id) {
        Author author = authorRepository.findById(id).orElseThrow(()->new EntityNotFoundException());
        AuthorDetResDto authorDetResDto = author.detFromEntity();

        return authorDetResDto;
    }
}
