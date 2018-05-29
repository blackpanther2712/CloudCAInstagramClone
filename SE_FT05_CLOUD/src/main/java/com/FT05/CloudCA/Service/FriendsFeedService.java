package com.FT05.CloudCA.Service;

import com.FT05.CloudCA.Entity.Post;
import com.FT05.CloudCA.Entity.User;
import com.FT05.CloudCA.Repositories.PostRepository;
import com.FT05.CloudCA.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FriendsFeedService {


    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserService userService;





    public List<Post> getFreiendsFeed(User user){
        List<Post> friendsPost = new ArrayList<>();
        List<User> userList = getFriendsList(user);
        List<Post> postList = postRepository.findAllOrderByDateAsc();
        for (Post post: postList) {
            if(userList.contains(post.getUser())) {
                friendsPost.add(post);

            }
        }

        return friendsPost;
    }

    public List<User> getFriendsList(User user){
        List<User> userList = new ArrayList<>();


        //User user = userRepository.findByUserId(4L); // replace with current user
        userList = user.getFollowing();
        System.out.println("user size "+ user.getFollowing().size());
        /*System.out.println("userlist"+userList);*/
        userList.add(user);
        System.out.println("userlist1"+userList);
        return userList;

    }

}


