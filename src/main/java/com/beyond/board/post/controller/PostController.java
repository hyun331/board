package com.beyond.board.post.controller;

import com.beyond.board.post.dto.PostDetResDto;
import com.beyond.board.post.dto.PostListResDto;
import com.beyond.board.post.dto.PostSaveReqDto;
import com.beyond.board.post.dto.PostUpdateDto;
import com.beyond.board.post.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/post")
@Slf4j
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
//    @GetMapping("/list")
//    public String showPostList(Model model){
//        model.addAttribute("postList", postService.postList());
//        return "post/post_list";
//    }

    //페이징 처리 rest api방식
    @GetMapping("/list/page")
    @ResponseBody
    //Pageable 요청 방법 : localhost:8080/post/list?size=10&page=0    size, page가 Pageable 객체에 바인딩
    public Page<PostListResDto> postListTest(@PageableDefault(size=10, sort="createdTime", direction = Sort.Direction.DESC) Pageable pageable){
        return postService.postListPage(pageable);
    }
    //페이징 처리 mvc 방식
    @GetMapping("/list")
    public String showPostList(Model model, @PageableDefault(size=10, sort="createdTime", direction = Sort.Direction.DESC) Pageable pageable){
        model.addAttribute("postList", postService.postListPage(pageable));
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
//        log.info("get 요청이고 parameter : "+id);
//        log.info("method명 : detailPost");
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
