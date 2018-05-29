package com.FT05.CloudCA.Service;

import com.FT05.CloudCA.Entity.Post;
import com.FT05.CloudCA.Entity.User;
import com.FT05.CloudCA.Repositories.PostRepository;
import com.FT05.CloudCA.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FriendsFeedService {


    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;
    //Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    List<User> userList;
    List<Post> friendsPost = new ArrayList<>();
    public List<Post> getFreiendsFeed(){
        getFriendsList();
        List<Post> postList = postRepository.findAllOrderByDateAsc();
        for (Post post: postList) {
            if(userList.contains(post.getUser())) {
                friendsPost.add(post);

            }
        }

        return friendsPost;

    }

    public void getFriendsList(){

        User user = userRepository.findByUserId(2L); // replace with current user
        userList = user.getFollowing();
        userList.add(user);

        System.out.println("userlist"+userList);

    }
    

    /*public List<Post> getCurrentUserFeed(){
         = postRepository.findByUserId(1L); // replace with current user

    }*/
}


