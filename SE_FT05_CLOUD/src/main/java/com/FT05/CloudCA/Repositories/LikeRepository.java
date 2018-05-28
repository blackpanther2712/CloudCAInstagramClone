package com.FT05.CloudCA.Repositories;

import com.FT05.CloudCA.Entity.Like;
import com.FT05.CloudCA.Entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface LikeRepository extends JpaRepository<Like,Long> {

    @Query(value = "SELECT * FROM likes WHERE POST_ID = ?1", nativeQuery = true)
    Like findByPostId(Long postId);

    @Modifying
    @Query(value = "DELETE FROM likes WHERE POST_ID = ?1 and user_id =?2", nativeQuery = true)
    void deleteByLike(Long postId, Long userId);






}
