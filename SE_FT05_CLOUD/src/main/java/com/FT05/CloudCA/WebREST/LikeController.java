package com.FT05.CloudCA.WebREST;

import com.FT05.CloudCA.Entity.Post;
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

@RestController
public class LikeController {

    @Autowired
    PostRepository postRepository;

    @Autowired
    LikeRepository likeRepository;
    @Autowired
    LikeService likeService;
    @Autowired
    UserService userService;

    /* This controller is invoked when a currently logged in user clicks a like/unlike button for his and their follower photos.
    It will update the Database whenever user performs this action. */

    @RequestMapping(value = "/likes/{postId}", method = RequestMethod.GET)
    public String showGuestList(@PathVariable("postId") String postId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        likeService.likeChecker(postId, user);
        return "index";
    }
}
