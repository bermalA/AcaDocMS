package com.acadoc.acadocms.repository;

import com.acadoc.acadocms.model.Role;
import com.acadoc.acadocms.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRole(UserRole role);
}
