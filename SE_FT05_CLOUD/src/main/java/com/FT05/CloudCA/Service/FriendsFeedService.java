package com.FT05.CloudCA.Service;

import com.FT05.CloudCA.Entity.Like;
import com.FT05.CloudCA.Entity.Post;
import com.FT05.CloudCA.Entity.User;
import com.FT05.CloudCA.Repositories.LikeRepository;
import com.FT05.CloudCA.Repositories.PostRepository;
import com.FT05.CloudCA.Repositories.UserRepository;
import org.joda.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
                Like like = likeRepository.findByPostId(post.getId(), user.getId());
                if(like != null) {
                    post.setLikeIndicator("L");
                    Long ago = getDateDiff(post.getCreatedDatetime(), new Date(), TimeUnit.MINUTES);
                    int iago = ago.intValue();
                    post.setAgo(calculateAgo(iago));
                }
                else {
                    post.setLikeIndicator("U");
                    Long ago = getDateDiff(post.getCreatedDatetime(), new Date(), TimeUnit.MINUTES);
                    int iago = ago.intValue();
                    post.setAgo(calculateAgo(iago));
                    System.out.println("testing1 "+ calculateAgo(iago));
                }

                friendsPost.add(post);
            }
        }

        return friendsPost;
    }

    public List<User> getFriendsList(User user){
        List<User> userList = new ArrayList<>();
        userList = user.getFollowing();
        userList.add(user);
        return userList;

    }

    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
    }

    public String calculateAgo(int ago) {
        if(ago > 60) {
            int i = ago/60;
            return i+" hour ago";
        }
        else {
            return ago+" minutes ago";
        }

    }
}


