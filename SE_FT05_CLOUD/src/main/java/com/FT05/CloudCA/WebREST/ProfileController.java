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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProfileController {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    UserService userService;

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




}
