package com.FT05.CloudCA.Service;

import com.FT05.CloudCA.AWS.AmazonClient;
import com.FT05.CloudCA.Entity.Like;
import com.FT05.CloudCA.Entity.Post;
import com.FT05.CloudCA.Repositories.LikeRepository;
import com.FT05.CloudCA.Repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.FT05.CloudCA.Entity.Role;
import com.FT05.CloudCA.Entity.User;
import com.FT05.CloudCA.Repositories.RoleRespository;
import com.FT05.CloudCA.Repositories.UserRepository;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;

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

    @Autowired
    private AmazonClient amazonClient;

    @Autowired
    LikeRepository likeRepository;


    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void saveUser(User user) throws IOException {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        user.setRegisteredDate(new Date());
        Role userRole = roleRepository.findByRole("ADMIN");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        user.setBio("");
        user.setHighSchool("");
        user.setUniversity("");
        user.setCurrentCity("");
        user.setCountry("");
        userRepository.save(user);
        amazonClient.elasticAdd(user);
    }

    @Override
    public User getSelectedUser(Long uid) {
        return userRepository.findByUserId(uid);
    }

    @Override
    public List<Post> getSelectedUserPosts(Long uid, User currentUser) {
        List<Post> postList = postRepository.findByUserId(uid);
        for (Post post : postList) {
            Like like = likeRepository.findByPostId(post.getId(), currentUser.getId());
            if(like != null) {
                System.out.println("like profile" );
                post.setLikeIndicator("L");
            }
            else {
                System.out.println("unlike profile" );
                post.setLikeIndicator("U");
            }

        }
        return postList;
    }

    @Override
    public void updateCurrentUserDetails(Long uid, Long id) {
        //checkCurrentLoginUser(id);

    }


    @Transactional
    @Override
    public void updateMyProfile(User user) throws IOException {
        amazonClient.elasticUpdate(user);
        userRepository.updateByUserId(user.getBio(), user.getFirstname(), user.getLastname(), user.getCurrentCity(), user.getHighSchool(), user.getUniversity(), user.getId());

    }

    @Transactional
    @Override
    public void updateMyProfilePicture(User user) throws IOException {
        amazonClient.elasticUpdate(user);
        userRepository.updatePicByUserId(user.getImage(), user.getId());
    }



    @Override
    public void getFollowersList(User currentUser, User selectedUser) {
        List<User> userList = currentUser.getFollowing();

        for (User user : userList) {
            if(user.getId() == selectedUser.getId()) {
                selectedUser.setFollowIndicator("F");
                break;
            }
        }


        if(selectedUser.getFollowIndicator() != null) {
            //Friends
        }
        else{
            selectedUser.setFollowIndicator("N");
        }

        System.out.println("follow1 "+ selectedUser.getFollowIndicator());
    }

    @Override
    public void updateFollowerList(User user, String followerId) {
        List<User> currentUserFollowerList = user.getFollowing();
        if(followerId.toLowerCase().endsWith("d")) {
            Long followerIds =  Long.parseLong(followerId.substring(0, followerId.length()-1));
            User follwer = userRepository.findByUserId(followerIds);
            currentUserFollowerList.add(follwer);
            user.setFollowing(currentUserFollowerList);
            userRepository.save(user);
        }

        else {
            Long followerIds =  Long.parseLong(followerId);
            User follwer = userRepository.findByUserId(followerIds);
            currentUserFollowerList.remove(follwer);
            user.setFollowing(currentUserFollowerList);
            userRepository.save(user);

        }
    }

}