package com.beyond.board.author.service;

import com.beyond.board.author.domain.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
//doLogin 하면 UserDetailsService를 구현한 클래스를 찾아와서 loadUserByUsername 실행
public class LoginService implements UserDetailsService {

    @Autowired
    private AuthorService authorService;

    @Override
    //로그인한 user를 가져오는 메서드
    //username은 입력한 id. 우리는 id로 email을 사용
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Author author =authorService.authorFindByEmail(username);
        //권한설정. 한 사람이 여러 개의 권한을 가질 수 있기 때문에 List로 구현. 근데 우리는 권한 한개만 구현함
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_"+author.getRole()));
        //new User(username, pw, 권한)
        return new User(author.getEmail(), author.getPassword(), authorities);
    }
}
