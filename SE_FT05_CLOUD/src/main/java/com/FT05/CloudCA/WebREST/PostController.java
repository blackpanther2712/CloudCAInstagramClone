package com.FT05.CloudCA.WebREST;

import com.FT05.CloudCA.AWS.AmazonClient;
import com.FT05.CloudCA.Entity.Post;
import com.FT05.CloudCA.Entity.User;
import com.FT05.CloudCA.Repositories.PostRepository;
import com.FT05.CloudCA.Service.FriendsFeedService;
import com.FT05.CloudCA.Service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.Collections;
import java.util.Date;

import static com.FT05.CloudCA.WebREST.SearchController.REST_SERVICE_URI;

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
        System.out.println(userService.findUserByEmail(auth.getName()).getEmail());

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
    public String uploadFile(Model model, @ModelAttribute Post post, @RequestParam("file") MultipartFile file) throws IOException, JSONException {
        String imageUrl = this.amazonClient.uploadFile(file);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = userService.findUserByEmail(auth.getName());
        post.setUser(user);
        post.setCreatedDatetime(new Date());
        post.setImageUrl(imageUrl);

        //Calling Lambda
        ObjectMapper mapper = new ObjectMapper();
        String host = "https://m68ercuxr1.execute-api.us-east-1.amazonaws.com/rekognize/filter";
        String json = mapper.writeValueAsString(post);

        System.out.println("json: " + json);

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.postForObject(host, json, String.class);

        JSONObject jsonObject = new JSONObject(response);
        if(jsonObject.get("image_url") != null){
            model.addAttribute("Success", "Successfully Uploaded!");
            System.out.println("Sucess, Image Uploaded!");
        }
        else {
            model.addAttribute("Failure", "Image violates Facegram policy, Please try different image");
            System.out.println("Failed, Bad Image!");
        }
        postRepository.save(post);
        return "redirect:/home";
    }
}

