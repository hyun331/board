package com.beyond.board.author.controller;

import com.beyond.board.author.domain.Author;
import com.beyond.board.author.dto.AuthorDetResDto;
import com.beyond.board.author.dto.AuthorSaveReqDto;
import com.beyond.board.author.dto.AuthorListResDto;
import com.beyond.board.author.dto.AuthorUpdateDto;
import com.beyond.board.author.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Controller
@RequestMapping("/author")
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
//rest api
//    @PostMapping("/create")
//    public String authorCreate(@RequestBody AuthorSaveReqDto dto){
//
//        Author author = authorService.createAuthor(dto);
//        return author.getId()+"번 회원가입 완료";
//    }

    @PostMapping("/create")
    public String authorCreate(AuthorSaveReqDto dto){

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
        try{
            model.addAttribute("author", authorService.showAuthorDetail(id));

        }catch(EntityNotFoundException e){
            e.printStackTrace();

        }
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
