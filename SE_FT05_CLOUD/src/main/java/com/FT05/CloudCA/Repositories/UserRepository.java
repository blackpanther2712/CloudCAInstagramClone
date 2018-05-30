package com.FT05.CloudCA.Repositories;

import com.FT05.CloudCA.Entity.Like;
import com.FT05.CloudCA.Entity.User;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete


public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM users WHERE email = ?1", nativeQuery = true)
    User findByEmail(String email);

    @Query(value = "SELECT * FROM users WHERE ID = ?1", nativeQuery = true)
    User findByUserId(Long userId);

    @Modifying
    @Query(value = "UPDATE users SET bio = ?1, current_city = ?2, firstname = ?3, high_school = ?4, university = ?5, image_url = ?6 WHERE id = ?7", nativeQuery = true)
    void updateUser(String bio, String city, String fName, String lName, String school, String university, String imageurl, Long id);
    

}