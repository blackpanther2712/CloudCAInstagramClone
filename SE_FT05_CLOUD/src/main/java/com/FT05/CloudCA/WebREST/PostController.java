package com.FT05.CloudCA.WebREST;

import com.FT05.CloudCA.AWS.AmazonClient;
import com.FT05.CloudCA.Entity.Post;
import com.FT05.CloudCA.Entity.User;
import com.FT05.CloudCA.Repositories.PostRepository;
import com.FT05.CloudCA.Repositories.UserRepository;
import com.FT05.CloudCA.Service.FriendsFeedService;
import com.FT05.CloudCA.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.Date;

@Controller
public class PostController {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private AmazonClient amazonClient;

    @Autowired
    FriendsFeedService friendsFeedService;

    @Autowired
    PostController(AmazonClient amazonClient) {
        this.amazonClient = amazonClient;
    }



    @GetMapping("/home")
    public String showPage(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //User user = userService.findUserByEmail(auth.getName());
        System.out.println(userService.findUserByEmail(auth.getName()).getEmail());
        model.addAttribute("userName",userService.findUserByEmail(auth.getName()).getFirstname());
        model.addAttribute("user", userService.findUserByEmail(auth.getName()));
        if(friendsFeedService.getFreiendsFeed(userService.findUserByEmail(auth.getName())) != null){
            model.addAttribute("post",new Post());
            model.addAttribute("getpost",friendsFeedService.getFreiendsFeed(userService.findUserByEmail(auth.getName())));
        }
        else {
            model.addAttribute("post",new Post());
            model.addAttribute("getpost",postRepository.findAll());
        }

        return "index";
    }

    @PostMapping("/home")
    public String uploadFile(@ModelAttribute Post post, @RequestParam("file") MultipartFile file) {
        String imageUrl = this.amazonClient.uploadFile(file);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = userService.findUserByEmail(auth.getName());
        post.setUser(user);
        post.setCreatedDatetime(new Date());
        post.setImageUrl(imageUrl);
        postRepository.save(post);
        return "redirect:/home";
    }

}
