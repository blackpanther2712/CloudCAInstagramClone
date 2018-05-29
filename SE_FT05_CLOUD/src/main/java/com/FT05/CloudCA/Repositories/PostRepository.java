package com.FT05.CloudCA.Repositories;

import com.FT05.CloudCA.Entity.Like;
import com.FT05.CloudCA.Entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long>{

    @Query(value = "SELECT * FROM posts WHERE ID = ?1", nativeQuery = true)
    Post findByPostId(Long postId);

    @Query(value = "SELECT * FROM posts WHERE USER_ID =?1", nativeQuery = true)
    List<Post> findByUserId(Long userId);


    @Query(value = "SELECT * FROM posts ORDER BY created_date DESC ", nativeQuery = true)
    List<Post> findAllOrderByDateDesc();


}
