package com.beyond.board.post.service;

import com.beyond.board.author.domain.Author;
import com.beyond.board.author.service.AuthorService;
import com.beyond.board.post.domain.Post;
import com.beyond.board.post.dto.PostDetResDto;
import com.beyond.board.post.dto.PostListResDto;
import com.beyond.board.post.dto.PostSaveReqDto;
import com.beyond.board.post.dto.PostUpdateDto;
import com.beyond.board.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        String appointment = null;
        LocalDateTime appointmentTime = null;
        System.out.println(dto);
        if(dto.getAppointment().equals("Y") && !dto.getAppointmentTime().isEmpty()){
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            appointmentTime = LocalDateTime.parse(dto.getAppointmentTime(), dateTimeFormatter);
            LocalDateTime now = LocalDateTime.now();
            if(appointmentTime.isBefore(now)){
                throw new IllegalArgumentException("시간 입력이 잘못되었습니다.");
            }
        }

        Post post = postRepository.save(dto.toEntity(author, appointmentTime));
        return post;

    }
    public List<PostListResDto> postList(){
        List<Post> postList = postRepository.findAllFetch();
        List<PostListResDto> postResDtoList = new ArrayList<>();
        for(Post p : postList){
            postResDtoList.add(p.listFromEntity());
        }
        return postResDtoList;
    }



    //Page는 리스트로 안해도 됨. 알아서 내부적으로 리스트형태로 해줌
    public Page<PostListResDto> postListPage(Pageable pageable) {
        //findAll의 리턴타입을 Page<>로 재정의 해야 함 -> postRepository에서
        Page<Post> posts = postRepository.findByAppointment(pageable, "N");
        Page<PostListResDto> postListResDtos = posts.map(a->a.listFromEntity());    //Post객체 -> DTO로 변환
        return postListResDtos;

    }

    public PostDetResDto postDetail(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()->new EntityNotFoundException("post가 존재하지 않습니다."));
        return post.detFromEntity();
    }


    public void updatePost(Long id, PostUpdateDto dto) {
        Post post = postRepository.findById(id).orElseThrow(()->new EntityNotFoundException("post is not found"));
        post.updatePost(dto);
        postRepository.save(post);
    }


}


