package com.FT05.CloudCA.WebREST;

import com.FT05.CloudCA.Entity.Post;
import com.FT05.CloudCA.Repositories.LikeRepository;
import com.FT05.CloudCA.Repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LikeController {
    @Autowired
LikeRepository likeRepository;
    @Autowired
    PostRepository postRepository;


    @RequestMapping(value = "/likes/{postId}", method = RequestMethod.GET)
    public String showGuestList(@PathVariable("postId") String postId) {
        Long postId1 = Long.parseLong(postId);
        System.out.println(postId);
        return "index";
    }
}
