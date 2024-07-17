package com.beyond.board.author.controller;

import com.beyond.board.author.domain.Author;
import com.beyond.board.author.dto.AuthorDetResDto;
import com.beyond.board.author.dto.AuthorReqDto;
import com.beyond.board.author.dto.AuthorResDto;
import com.beyond.board.author.service.AuthorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/author")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService){
        this.authorService = authorService;
    }

    @PostMapping("/create")
    public String createAuthor(@RequestBody AuthorReqDto authorReqDto){
        try{
            authorService.createAuthor(authorReqDto);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }
        return "ok";
    }

    @GetMapping("/list")
    public List<AuthorResDto> showAuthorList(){
        List<AuthorResDto> authorResDtoListList = authorService.showAuthorList();
        return authorResDtoListList;
    }

    @GetMapping("/detail/{id}")
    public AuthorDetResDto showAuthorDetail(@PathVariable Long id){
        AuthorDetResDto authorDetResDto = null;
        try{
            authorDetResDto = authorService.showAuthorDetail(id);

        }catch(EntityNotFoundException e){
            e.printStackTrace();

        }
        return authorDetResDto;

    }
}
