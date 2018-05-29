package com.FT05.CloudCA.Repositories;

import com.FT05.CloudCA.Entity.Like;
import com.FT05.CloudCA.Entity.User;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete


public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM users WHERE ID = ?1", nativeQuery = true)
    User findByEmail(String email);

    @Query(value = "SELECT * FROM users WHERE ID = ?1", nativeQuery = true)
    User findByUserId(Long userId);
    

}