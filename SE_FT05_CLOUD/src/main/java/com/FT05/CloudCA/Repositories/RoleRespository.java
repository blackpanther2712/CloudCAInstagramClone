package com.FT05.CloudCA.Repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.FT05.CloudCA.Entity.Role;

@Repository("roleRepository")
public interface RoleRespository extends JpaRepository<Role , Integer>{
    Role findByRole(String role);
}

