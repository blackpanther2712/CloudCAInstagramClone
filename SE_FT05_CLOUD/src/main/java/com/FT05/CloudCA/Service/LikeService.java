package com.FT05.CloudCA.Service;

import com.FT05.CloudCA.Entity.Like;
import com.FT05.CloudCA.Entity.Post;
import com.FT05.CloudCA.Entity.User;
import com.FT05.CloudCA.Repositories.LikeRepository;
import com.FT05.CloudCA.Repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LikeService {

    @Autowired
    PostRepository postRepository;
    @Autowired
    LikeRepository likeRepository;

    //Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    @Transactional
    public void likeChecker(String postId){

        Post post;
        if(postId.toLowerCase().endsWith("d")) {

            Long postIds =  Long.parseLong(postId.substring(0, postId.length()-1));
            post = postRepository.findByPostId(postIds);
            Like like = new Like();
            like.setPost(post);
            //like.setUser(auth.getPrincipal()/getDetails());
            User user = new User();
            user.setId(1L);
            like.setUser(user);
            likeRepository.save(like);
            //System.out.println(likeRepository.findByPostId(postIds));
        }

        else{

            Long postIds =  Long.parseLong(postId);
            Long userId = 1L;
            likeRepository.deleteByLike(postIds, userId);


        }


    }

}
