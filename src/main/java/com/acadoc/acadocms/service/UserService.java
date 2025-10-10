package com.acadoc.acadocms.service;

import com.acadoc.acadocms.model.Role;
import com.acadoc.acadocms.model.User;
import com.acadoc.acadocms.model.UserRole;
import com.acadoc.acadocms.repository.RoleRepository;
import com.acadoc.acadocms.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Transactional
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User registerUser(String fullName, String email, String password) {
        if(userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already exists");
        }
        User user = new User();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode(password));
        return userRepository.save(user);
    }

    public void assignRoleToUser(Long userId, UserRole roleType) {
        User user = getUserById(userId);
        Role role = roleService.getRoleByType(roleType);

        user.getRoles().add(role);
        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        if(!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }

    @Transactional
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Transactional
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found "));
    }

    @Transactional
    public List<User> searchUsers(String keyword){
        return userRepository.searchUsers(keyword);
    }

}
