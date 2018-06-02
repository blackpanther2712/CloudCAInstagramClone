package com.FT05.CloudCA.WebREST;

import com.FT05.CloudCA.Entity.User;
import com.FT05.CloudCA.Repositories.LikeRepository;
import com.FT05.CloudCA.Repositories.PostRepository;
import com.FT05.CloudCA.Service.LikeService;
import com.FT05.CloudCA.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class FollowController {

    @Autowired
    PostRepository postRepository;

    @Autowired
    LikeRepository likeRepository;
    @Autowired
    LikeService likeService;
    @Autowired
    UserService userService;

    @RequestMapping(value = "/follow/{id}", method = RequestMethod.GET)
    public String updateFollowerList(@PathVariable("id") String followerId) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        userService.updateFollowerList(user, followerId);
        String result = "/profile/"+followerId;
        return result;
    }

}
