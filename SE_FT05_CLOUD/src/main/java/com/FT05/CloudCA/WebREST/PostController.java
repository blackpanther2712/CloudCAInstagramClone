package com.FT05.CloudCA.WebREST;

import com.FT05.CloudCA.AWS.AmazonClient;
import com.FT05.CloudCA.Entity.Post;
import com.FT05.CloudCA.Entity.User;
import com.FT05.CloudCA.Repositories.PostRepository;
import com.FT05.CloudCA.Repositories.UserRepository;
import com.FT05.CloudCA.Service.FriendsFeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.ZonedDateTime;
import java.util.Date;

@Controller
public class PostController {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AmazonClient amazonClient;

    @Autowired
    FriendsFeedService friendsFeedService;

    @Autowired
    PostController(AmazonClient amazonClient) {
        this.amazonClient = amazonClient;
    }

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    @GetMapping("/home")
    public String showPage(Model model){
        if(friendsFeedService.getFreiendsFeed() != null){
            model.addAttribute("post",new Post());
            model.addAttribute("getpost",friendsFeedService.getFreiendsFeed());
        }
        else {
            model.addAttribute("post",new Post());
            model.addAttribute("getpost",postRepository.findAll());
        }

        /*model.addAttribute("post",new Post());
        model.addAttribute("getpost",postRepository.findAll());*/

        return "index";
    }

    @PostMapping("/home")
    public String uploadFile(@ModelAttribute Post post, @RequestParam("file") MultipartFile file) {
        String imageUrl = this.amazonClient.uploadFile(file);
        /*User user = new User();

        user.setCurrentCity("Singapore");
        user.setHighSchool("NUS");
        user.setBio("bio");
//        user.setTokenId("Cognito Token");
       user.setUniversity("ISS");
        userRepository.save(user);*/

        System.out.println(auth.getDetails());
        /*post.setUser(user);
        post.setCreatedDatetime(new Date());
        post.setImageUrl(imageUrl);
        postRepository.save(post);*/
        return "index";
    }

}
