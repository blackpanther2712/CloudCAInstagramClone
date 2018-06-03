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

    /* This controller is responsible for retrieving a selected user profile details from database */

    @RequestMapping(value = "/profile/{id}", method = RequestMethod.GET)
    public ModelAndView showProfile(@PathVariable("id") String id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findUserByEmail(auth.getName());
        ModelAndView model = new ModelAndView();
        Long uid = Long.parseLong(id);
        userService.updateCurrentUserDetails(uid, currentUser.getId());
        User selectedUser = userService.getSelectedUser(uid);
        userService.getFollowersList(currentUser, selectedUser);
        model.addObject("userPosts",userService.getSelectedUserPosts(uid, currentUser));
        model.addObject("userDetails", selectedUser);
        model.addObject("currentUser", currentUser);
        model.setViewName("profile");
        return model;
    }


    /* This controller is invoked whenever user clicks for follow/unfollow button and the respective
    results will be updated in database */

    @RequestMapping(value = "/follow/{id}", method = RequestMethod.GET)
    public String updateFollowerList(@PathVariable("id") String followerId) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        userService.updateFollowerList(user, followerId);
        String result = "/profile/"+followerId;
        return result;
    }


    /* This controller is responsible for retrieving the currently loggin in user's profile detrails from database */

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


/*
    This controller is invoked whenever user clicks on update profile picture and it will store the newly selected picture in s3 and save that location in RDS database
*/

    @PostMapping("/updateprofilepic")
    public String updateMyProfilePicture(@ModelAttribute User updUser, @RequestParam("profilepic") MultipartFile file) throws IOException {
        String imageUrl = this.amazonClient.uploadFile(file);
        updUser.setImage(imageUrl);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        updUser.setId(user.getId());
        updUser.setFirstname(user.getFirstname());
        updUser.setLastname(user.getLastname());
        updUser.setEmail(user.getEmail());
        userService.updateMyProfilePicture(updUser);
        return "redirect:/home";
    }




}
