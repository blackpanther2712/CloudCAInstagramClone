package com.FT05.CloudCA.Service;

import com.FT05.CloudCA.Entity.Like;
import com.FT05.CloudCA.Entity.Post;
import com.FT05.CloudCA.Entity.User;
import com.FT05.CloudCA.Repositories.LikeRepository;
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
    LikeRepository likeRepository;

    @Autowired
    UserService userService;



    public List<Post> getFreiendsFeed(User user){
        List<Post> friendsPost = new ArrayList<>();
        List<User> userList = getFriendsList(user);
        List<Post> postList = postRepository.findAllOrderByDateDesc();
        for (Post post: postList) {
            if(userList.contains(post.getUser())) {
                Like like = likeRepository.findByPostId(post.getId());
                if(like != null && like.getUser().getId() == user.getId()) {
                    System.out.println("like" );
                    post.setLikeIndicator("L");
                }
                else {
                    System.out.println("unlike" );
                    post.setLikeIndicator("U");
                }
                friendsPost.add(post);

            }
        }

        return friendsPost;
    }

    public List<User> getFriendsList(User user){
        List<User> userList = new ArrayList<>();

        userList = user.getFollowing();
        System.out.println("user size "+ user.getFollowing().size());
        userList.add(user);
        System.out.println("userlist1"+userList);
        return userList;

    }

}


