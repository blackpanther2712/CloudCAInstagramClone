package com.FT05.CloudCA;

import com.FT05.CloudCA.Entity.Post;
import com.FT05.CloudCA.Entity.User;
import com.FT05.CloudCA.Repositories.PostRepository;
import com.FT05.CloudCA.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FirstSpringApplication implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PostRepository postRepository;
	public static void main(String[] args) {
		SpringApplication.run(FirstSpringApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		User user = new User();
		user.setName("Rakesh");
		user.setCurrentCity("Singapore");
		user.setHighSchool("NUS");
		user.setBio("bio");
		user.setTokenId("Cognito Token");
		user.setUniversity("ISS");
		userRepository.save(user);

		Post post = new Post();
		post.setUser(user);
		postRepository.save(post);

	}
}
