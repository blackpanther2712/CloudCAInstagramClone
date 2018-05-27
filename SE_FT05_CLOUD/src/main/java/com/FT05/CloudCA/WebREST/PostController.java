package com.FT05.CloudCA.WebREST;

import com.FT05.CloudCA.AWS.AmazonClient;
import com.FT05.CloudCA.Entity.Post;
import com.FT05.CloudCA.Entity.User;
import com.FT05.CloudCA.Repositories.PostRepository;
import com.FT05.CloudCA.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    PostController(AmazonClient amazonClient) {
        this.amazonClient = amazonClient;
    }

    @GetMapping("/home")
    public String showPage(Model model){
        model.addAttribute("post",new Post());
        model.addAttribute("getpost",postRepository.findAll());
        return "index";
    }

    @PostMapping("/home")
    public String uploadFile(@ModelAttribute Post post, @RequestParam("file") MultipartFile file) {
        String imageUrl = this.amazonClient.uploadFile(file);
        User user = new User();
        user.setName("Gowtham");
        user.setCurrentCity("Singapore");
        user.setHighSchool("NUS");
        user.setBio("bio");
        user.setTokenId("Cognito Token");
        user.setUniversity("ISS");
        userRepository.save(user);

        post.setUser(user);
        post.setCreatedDatetime(new Date());
        post.setImageUrl(imageUrl);
        postRepository.save(post);
        return "index";
    }


    @RequestMapping(value = "/likes/{postId}", method = RequestMethod.GET)
    public String showGuestList(@PathVariable("postId") String postId) {

        System.out.println(postId);
        return "index";
    }
}
