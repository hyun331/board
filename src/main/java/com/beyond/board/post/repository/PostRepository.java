package com.beyond.board.post.repository;

import com.beyond.board.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    //jpql를 적용하여 네이밍을 통한 방식이 아닌 메서드 생성
    //join문에 author 안 넣어도 됨
    //select a.*, p.* from post p left join author a on p.authorid=a.id
    @Query("select p from Post p left join fetch p.author")
    List<Post> findAllFetch();


    //select p.* from post p left join author a on p.author_id=a.id;
    //아래의 join문은 author객체를 통한 조건문으로 post를 filtering할 때 사용
    // -> 이거는 author_email 알려면 다시 author찾는 쿼리 발생->N+1이슈
    @Query("select p from Post p left join p.author")
    List<Post> findAllLeftJoin();

}
