package com.FT05.CloudCA.WebREST;

import com.FT05.CloudCA.AWS.AmazonClient;
import com.FT05.CloudCA.Entity.User;
import com.FT05.CloudCA.Repositories.PostRepository;
import com.FT05.CloudCA.Repositories.UserRepository;
import com.FT05.CloudCA.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class UpdateProfileController {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    private AmazonClient amazonClient;


    @PostMapping("/myprofile")
    public String updateMyProfile(@ModelAttribute User updUser) {
        userService.updateMyProfile(updUser);
        return "redirect:/home";
    }
}
