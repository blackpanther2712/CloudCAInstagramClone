package com.FT05.CloudCA.WebREST;

import com.FT05.CloudCA.AWS.AmazonClient;
import com.FT05.CloudCA.Entity.Post;
import com.FT05.CloudCA.Repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class PostController {
    @Autowired
    private PostRepository postRepository;
    private AmazonClient amazonClient;

    @Autowired
    PostController(AmazonClient amazonClient) {
        this.amazonClient = amazonClient;
    }

    @GetMapping("/home")
    public String showPage(Model model){
        model.addAttribute("post",new Post());
        return "index";
    }

    @PostMapping("/home")
    public String uploadFile(@RequestPart(value = "file") MultipartFile file) {
        String s = this.amazonClient.uploadFile(file);

        return "index";
    }

}
