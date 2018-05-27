package com.FT05.CloudCA.WebREST;

import com.FT05.CloudCA.Entity.Post;
import com.FT05.CloudCA.Repositories.LikeRepository;
import com.FT05.CloudCA.Repositories.PostRepository;
import com.FT05.CloudCA.Service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
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


    @RequestMapping(value = "/likes/{postId}", method = RequestMethod.GET)
    public String showGuestList(@PathVariable("postId") String postId) {

        likeService.likeChecker(postId);
        return "index";
    }
}
