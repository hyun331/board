package com.beyond.board.author.controller;

import com.beyond.board.author.domain.Author;
import com.beyond.board.author.dto.AuthorDetResDto;
import com.beyond.board.author.dto.AuthorSaveReqDto;
import com.beyond.board.author.dto.AuthorListResDto;
import com.beyond.board.author.dto.AuthorUpdateDto;
import com.beyond.board.author.service.AuthorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Controller
@RequestMapping("/author")
@Slf4j
public class AuthorController {

    private final AuthorService authorService;



    @Autowired
    public AuthorController(AuthorService authorService){
        this.authorService = authorService;
    }

    @GetMapping("/register")
    public String authoRegisterView(){
        return "author/author_register";
    }

    @GetMapping("/login-screen")
    public String authorLoginScreen(){
        return "author/login-screen";
    }

//rest api
//    @PostMapping("/create")
//    public String authorCreate(@RequestBody AuthorSaveReqDto dto){
//
//        Author author = authorService.createAuthor(dto);
//        return author.getId()+"번 회원가입 완료";
//    }

    @PostMapping("/register")
    public String authorCreate(AuthorSaveReqDto dto){
        System.out.println("회원가입 컨트롤러");
        Author author = authorService.createAuthor(dto);
        return "redirect:/author/list";
    }


    //Rest api일때
//    @GetMapping("/list")
//    public List<AuthorListResDto> showAuthorList(){
//        return authorService.authorList();
//    }

    @GetMapping("/list")
    public String showAuthorList(Model model){
        model.addAttribute("authorList", authorService.authorList());
        return "author/author_list";
    }

    //rest일 때
//    @GetMapping("/detail/{id}")
//    public AuthorDetResDto showAuthorDetail(@PathVariable Long id){
//        AuthorDetResDto authorDetResDto = null;
//        try{
//            authorDetResDto = authorService.showAuthorDetail(id);
//
//        }catch(EntityNotFoundException e){
//            e.printStackTrace();
//
//        }
//        return authorDetResDto;
//
//    }

    @GetMapping("/detail/{id}")
    public String showAuthorDetail(@PathVariable Long id, Model model){
        AuthorDetResDto authorDetResDto = null;
//        try{
//            model.addAttribute("author", authorService.showAuthorDetail(id));
//
//        }catch(EntityNotFoundException e){
//            e.printStackTrace();
//            log.error(id+e.getMessage()); //이렇게 log에 남겨주기. 근데 예외 공통처리할거라 주석처리
//
//        }
//
//        log.info("get request. parameter : "+id);
//        log.info("method : showAuthorDetail");

        model.addAttribute("author", authorService.showAuthorDetail(id));

        return "author/author_detail";

    }

    //원래는 DeleteMapping 인데 지금 구현하기 복잡해서 GetMapping으로만 처리
    @GetMapping("/delete/{id}")
    public String authorDelete(@PathVariable Long id){
        authorService.delete(id);
        return "redirect:/author/list";
    }


    @PostMapping("/update/{id}")
    public String authorUpdate(@PathVariable Long id, AuthorUpdateDto authorUpdateDto){
        authorService.update(id, authorUpdateDto);
        return "redirect:/author/detail/"+id;
    }
}
