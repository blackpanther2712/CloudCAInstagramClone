package com.FT05.CloudCA;

import com.FT05.CloudCA.Entity.Post;
import com.FT05.CloudCA.Entity.User;
import com.FT05.CloudCA.Repositories.PostRepository;
import com.FT05.CloudCA.Repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Date;

@SpringBootApplication
public class FirstSpringApplication implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PostRepository postRepository;
	public static void main(String[] args) throws IOException {

		SpringApplication.run(FirstSpringApplication.class, args);
       	}

	@Override
	public void run(String... args) throws Exception {
		/*User user = new User();
		user.setName("Rakesh");
		user.setCurrentCity("Singapore");
		user.setHighSchool("ISS");
		user.setBio("bio");
		user.setTokenId("Cognito Token");
		user.setUniversity("SE");
		userRepository.save(user);

		Post post = new Post();
		Post post1 = new Post();

		post.setUser(user);
		post.setCaption("awesome");
		post.setCreatedDatetime(new Date());
		post1.setUser(user);
        post1.setCreatedDatetime(new Date());
		post1.setCaption("gowtham");
		postRepository.save(post);
        postRepository.save(post1);*/



	}
}
