package com.beyond.board.post.controller;

import com.beyond.board.post.dto.PostDetResDto;
import com.beyond.board.post.dto.PostListResDto;
import com.beyond.board.post.dto.PostSaveReqDto;
import com.beyond.board.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService){
        this.postService = postService;
    }


    @PostMapping("/create")
    public String postCreate(@RequestBody PostSaveReqDto dto){
        postService.postCreate(dto);
        return "ok";
    }


    @GetMapping("/list")
    public List<PostListResDto> showPostList(){
        return postService.postList();
    }

    @GetMapping("/detail/{id}")
    public PostDetResDto detailPost(@PathVariable Long id){
        return postService.postDetail(id);
    }
}
