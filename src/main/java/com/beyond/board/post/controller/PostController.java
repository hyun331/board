package com.beyond.board.post.controller;

import com.beyond.board.post.dto.PostDetResDto;
import com.beyond.board.post.dto.PostListResDto;
import com.beyond.board.post.dto.PostSaveReqDto;
import com.beyond.board.post.dto.PostUpdateDto;
import com.beyond.board.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService){
        this.postService = postService;
    }


    @GetMapping("/create")
    public String postCreateView(){
        return "post/post_register";
    }

    @PostMapping("/create")
    public String postCreate( PostSaveReqDto dto){
        postService.postCreate(dto);
        return "redirect:/post/list";
    }



    //rest api
//    @GetMapping("/list")
//    public List<PostListResDto> showPostList(){
//        return postService.postList();
//    }
    //mvc
    @GetMapping("/list")
    public String showPostList(Model model){
        model.addAttribute("postList", postService.postList());
        return "post/post_list";
    }

//rest api
//    @GetMapping("/detail/{id}")
//    public PostDetResDto detailPost(@PathVariable Long id){
//        return postService.postDetail(id);
//    }
    //mvc
    @GetMapping("/detail/{id}")
    public String detailPost(@PathVariable Long id, Model model){
        model.addAttribute("post", postService.postDetail(id));
        return "post/post_detail";
    }

    //수정
    @Transactional
    @PostMapping("/update/{id}")
    public String updatePost(@PathVariable Long id, PostUpdateDto dto){
        postService.updatePost(id, dto);
        return "redirect:/post/detail/"+id;
    }
}
