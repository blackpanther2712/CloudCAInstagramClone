package com.FT05.CloudCA.Repositories;

import com.FT05.CloudCA.Entity.Like;
import com.FT05.CloudCA.Entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends JpaRepository<Post, Long>{

    @Query(value = "SELECT * FROM posts WHERE ID = ?1", nativeQuery = true)
    Post findByPostId(Long postId);

}
