package com.beyond.board.author.service;

import com.beyond.board.author.domain.Author;
import com.beyond.board.author.dto.AuthorDetResDto;
import com.beyond.board.author.dto.AuthorSaveReqDto;
import com.beyond.board.author.dto.AuthorListResDto;
import com.beyond.board.author.dto.AuthorUpdateDto;
import com.beyond.board.author.repository.AuthorRepository;
import com.beyond.board.post.domain.Post;
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
        if(authorReqDto.getPassword().length()<8){
            throw new IllegalArgumentException("password too short");
        }
        Author author = authorReqDto.toEntity();
        //여기 코드엔 post객체를 db에 저장하는 코드가 없음.
        //casecade.persist가 해줌
        author.getPosts().add(Post.builder().author(author).title("가입인사").contents("안녕하세요 "+ authorReqDto.getName()+"입니다.").build());
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
        Author author = authorRepository.findById(id).orElseThrow(()->new EntityNotFoundException("member isn't found"));
        AuthorDetResDto authorDetResDto = new AuthorDetResDto();

        //Author에 있던 fromEntity를 바꿈
        return authorDetResDto.fromEntity(author);
    }


    public Author authorFindByEmail(String email){
        Author author = authorRepository.findByEmail(email).orElseThrow(()->new EntityNotFoundException("해당 email의 사용자는 없습니다."));
        return author;
    }

    @Transactional
    public void delete(Long id) {
//        Author deleteAuthor = authorRepository.findById(id).orElseThrow(()->new EntityNotFoundException("해당 Author가 존재하지 않습니다."));
//        authorRepository.delete(deleteAuthor);
        authorRepository.deleteById(id);
    }

    //Transactional 반드시 필요
    @Transactional
    public void update(Long id, AuthorUpdateDto authorUpdateDto) {
        Author author = authorRepository.findById(id).orElseThrow(()->new EntityNotFoundException("해당 Author가 존재하지 않습니다."));
        author.updateAuthor(authorUpdateDto);
        //jpa가 특정 엔티티의 변경을 자동으로 인지하고 변경사항을 db에 반영하는 것이 dirtychecking(변경감지)
        //즉 save안해줘도 됨.
//        authorRepository.save(author);

    }
}
