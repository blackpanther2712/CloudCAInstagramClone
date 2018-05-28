package com.FT05.CloudCA.Repositories;

import com.FT05.CloudCA.Entity.Like;
import com.FT05.CloudCA.Entity.User;
<<<<<<< HEAD
import org.springframework.stereotype.Repository;
=======
>>>>>>> 44792666ad016036648c8017fb5437d00897c4b7
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
<<<<<<< HEAD
@Repository("userrepository")
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
=======

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM users WHERE ID = ?1", nativeQuery = true)
    User findByUserId(Long userId);

>>>>>>> 44792666ad016036648c8017fb5437d00897c4b7
}