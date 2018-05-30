package com.FT05.CloudCA.Service;
import com.FT05.CloudCA.Entity.User;
public interface UserService {

    public User findUserByEmail(String email);

    public User findByTokenId (String tokenid);

    public void saveUser(User user);

}
