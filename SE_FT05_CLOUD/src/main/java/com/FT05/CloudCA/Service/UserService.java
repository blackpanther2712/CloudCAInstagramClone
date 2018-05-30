package com.FT05.CloudCA.Service;
import com.FT05.CloudCA.Entity.Post;
import com.FT05.CloudCA.Entity.User;

import java.util.List;

public interface UserService {


    public User findUserByEmail(String email);

    public void saveUser(User user);

    public User getSelectedUser(Long uid);

    public List<Post> getSelectedUserPosts(Long uid);

    public void updateCurrentUserDetails(Long uid, Long id);

}
