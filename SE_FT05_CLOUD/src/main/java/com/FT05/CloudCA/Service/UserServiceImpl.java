package com.FT05.CloudCA.Service;

import com.FT05.CloudCA.Entity.Post;
import com.FT05.CloudCA.Repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.FT05.CloudCA.Entity.Role;
import com.FT05.CloudCA.Entity.User;
import com.FT05.CloudCA.Repositories.RoleRespository;
import com.FT05.CloudCA.Repositories.UserRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRespository roleRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User findUserByEmail(String email) {
        System.out.println("aaaaaa" + userRepository.findByEmail(email));
        return userRepository.findByEmail(email);
    }

    @Override
    public void saveUser(User user) {
        user.setBio(user.getPassword());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        Role userRole = roleRepository.findByRole("ADMIN");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        userRepository.save(user);
    }

    @Override
    public User getSelectedUser(Long uid) {
        return userRepository.findByUserId(uid);
    }

    @Override
    public List<Post> getSelectedUserPosts(Long uid) {
        return postRepository.findByUserId(uid);
    }

    @Override
    public void updateCurrentUserDetails(Long uid, Long id) {
        //checkCurrentLoginUser(id);

    }

    @Transactional
    @Override
    public void updateMyProfile(User user) {
        userRepository.updateByUserId(user.getBio(), user.getCurrentCity(), user.getFirstname(), user.getLastname(), user.getHighSchool(), user.getUniversity(), user.getId());
        userRepository.save(user);

    }


}