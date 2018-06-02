package com.FT05.CloudCA.Service;
import com.FT05.CloudCA.Entity.Post;
import com.FT05.CloudCA.Entity.User;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

public interface UserService {


    public User findUserByEmail(String email);

    public void saveUser(User user) throws IOException;

    public User getSelectedUser(Long uid);

    public List<Post> getSelectedUserPosts(Long uid);

    public void updateCurrentUserDetails(Long uid, Long id);

    public void updateMyProfile(User user) throws IOException;

    public void updateMyProfilePicture(User updUser) throws IOException;

    public void getFollowersList(User userId, User selectedUser);

    void updateFollowerList(User user, String followerId);
}
