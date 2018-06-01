package com.FT05.CloudCA.WebREST;

import com.FT05.CloudCA.AWS.AmazonClient;
import com.FT05.CloudCA.Entity.Post;
import com.FT05.CloudCA.Entity.User;
import com.FT05.CloudCA.Repositories.PostRepository;
import com.FT05.CloudCA.Repositories.UserRepository;
import com.FT05.CloudCA.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
public class ProfileController {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AmazonClient amazonClient;

    @RequestMapping(value = "/profile/{id}", method = RequestMethod.GET)
    public ModelAndView showProfile(@PathVariable("id") String id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());

        ModelAndView model = new ModelAndView();
        Long uid = Long.parseLong(id);
        userService.updateCurrentUserDetails(uid, user.getId());
        User selectedUser = userService.getSelectedUser(uid);
        model.addObject("userPosts",userService.getSelectedUserPosts(uid));
        model.addObject("userDetails", selectedUser);
        model.setViewName("profile");
        return model;
    }


    @PostMapping("/myprofile")
    public String updateMyProfile(@ModelAttribute User updUser) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        updUser.setId(user.getId());
        updUser.setEmail(user.getEmail());
        updUser.setImage(user.getImage());
        userService.updateMyProfile(updUser);
        return "redirect:/home";
    }

    @PostMapping("/updateprofilepic")
    public String updateMyProfilePicture(@ModelAttribute User updUser, @RequestParam("profilepic") MultipartFile file) throws IOException {
        String imageUrl = this.amazonClient.uploadFile(file);
        updUser.setImage(imageUrl);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        updUser.setId(user.getId());
        updUser.setEmail(user.getEmail());
        userService.updateMyProfilePicture(updUser);
        return "redirect:/home";
    }


}
