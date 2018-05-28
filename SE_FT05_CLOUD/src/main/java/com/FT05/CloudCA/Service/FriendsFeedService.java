package com.FT05.CloudCA.Service;

import com.FT05.CloudCA.Entity.Post;
import com.FT05.CloudCA.Entity.User;
import com.FT05.CloudCA.Repositories.PostRepository;
import com.FT05.CloudCA.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendsFeedService {


    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;
    //Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    List<User> userList;

    public void getFreiendsFeed(){
        getFriendsList();
        /*for (User user: userList) {



        }*/
        List<Post> postList = postRepository.findAllOrderByDateAsc();
        for (Post post: postList) {
            if(userList.contains(post.getUser())) {

            }
        }


    }

    public void getFriendsList(){
        
        User user = userRepository.findByUserId(3L); // replace with current user
        userList = user.getFollowing();

        System.out.println("userlist"+userList);
        
    }
    

}


