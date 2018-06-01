package com.FT05.CloudCA.Repositories;

import com.FT05.CloudCA.Entity.Like;
import com.FT05.CloudCA.Entity.User;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete


public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM users WHERE email = ?1", nativeQuery = true)
    User findByEmail(String email);

    @Query(value = "SELECT * FROM users WHERE ID = ?1", nativeQuery = true)
    User findByUserId(Long userId);



    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE users SET bio = ?1, firstname = ?2, lastname = ?3, current_city = ?4, high_school = ?5, university = ?6 WHERE id = ?7", nativeQuery = true)
    void updateByUserId(String bio, String fName, String lName, String city, String hschool, String univ, Long id);


}