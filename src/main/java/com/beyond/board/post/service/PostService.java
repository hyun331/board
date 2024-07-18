package com.beyond.board.post.service;

import com.beyond.board.author.domain.Author;
import com.beyond.board.author.service.AuthorService;
import com.beyond.board.post.domain.Post;
import com.beyond.board.post.dto.PostDetResDto;
import com.beyond.board.post.dto.PostListResDto;
import com.beyond.board.post.dto.PostSaveReqDto;
import com.beyond.board.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    //이렇게 PostService에서 AuthorService를 필요로하고
    //AuthorService에서  PostServcie룰 필요로하면 -> 순환참조가 될 수 있음 -> authorRepository가 하도록

    //servcie or repository를 autowired할지는 상황에 따ㅓ라 다르다.
    //service에서 코드가 ㄱ고도화 되어있고 코드의 중복이 심할 경우 , service레이어를 autowired
    //그러나 순환 참조가 발생한 경우에는 repository를 autowired
    private final AuthorService authorService;


    @Autowired
    public PostService(PostRepository postRepository, AuthorService authorService){
        this.postRepository = postRepository;
        this.authorService = authorService;
    }

    //author service에서 author 객체를 찾아 post의 toEntity에 넘기고
    //jpa가 author객체에서 author_id를 찾아 db에는 author_id가 들어감

    public Post postCreate(PostSaveReqDto dto){
        Author author = authorService.authorFindByEmail(dto.getEmail());
        Post post = postRepository.save(dto.toEntity(author));
        return post;

    }
    public List<PostListResDto> postList(){
        List<Post> postList = postRepository.findAll();
        List<PostListResDto> postResDtoList = new ArrayList<>();
        for(Post p : postList){
            postResDtoList.add(p.listFromEntity());
        }
        return postResDtoList;
    }

    public PostDetResDto postDetail(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()->new EntityNotFoundException("post가 존재하지 않습니다."));
        return post.detFromEntity();
    }


}
