package com.FT05.CloudCA.Service;

import com.FT05.CloudCA.Entity.Like;
import com.FT05.CloudCA.Entity.Post;
import com.FT05.CloudCA.Entity.User;
import com.FT05.CloudCA.Repositories.LikeRepository;
import com.FT05.CloudCA.Repositories.PostRepository;
import com.FT05.CloudCA.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LikeService {

    @Autowired
    PostRepository postRepository;
    @Autowired
    LikeRepository likeRepository;

    @Autowired
    UserRepository userRepository;

    @Transactional
    public void likeChecker(String postId, User user){

        Post post;
        int count;
        if(postId.toLowerCase().endsWith("d")) {

            Long postIds =  Long.parseLong(postId.substring(0, postId.length()-1));
            post = postRepository.findByPostId(postIds);
            count = post.getCount() + 1;
            post.setCount(count);
            postRepository.save(post);
            Like like = new Like();
            like.setPost(post);
            like.setUser(user);
            likeRepository.save(like);

        }

        else{

            Long postIds =  Long.parseLong(postId);
            post = postRepository.findByPostId(postIds);
            count = post.getCount() - 1;
            post.setCount(count);
            postRepository.save(post);
            likeRepository.deleteByLike(postIds, user.getId());


        }


    }



}
