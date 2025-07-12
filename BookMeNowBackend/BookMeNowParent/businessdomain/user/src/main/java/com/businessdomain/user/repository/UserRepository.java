package com.businessdomain.user.repository;

import com.businessdomain.user.dto.UserDTO;
import com.businessdomain.user.model.User;
import com.businessdomain.user.model.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    Optional<User> findByEmail (String email);

    boolean existsByRole(UserRole role);

}
