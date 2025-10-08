package com.acadoc.acadocms.service;

import com.acadoc.acadocms.model.Role;
import com.acadoc.acadocms.model.UserRole;
import com.acadoc.acadocms.repository.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    @Transactional
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Role createRole(UserRole roleType) {
        if(roleRepository.findByRole(roleType).isPresent()){
            throw new RuntimeException("Role already exists");
        }
        Role role = new Role();
        role.setRole(roleType);

         return roleRepository.save(role);
    }

    @Transactional
    public Role getRoleByType(UserRole roleType) {
        return roleRepository.findByRole(roleType)
                .orElseThrow(() -> new RuntimeException("Role doesn't exists"));
    }

    public void initializeDefaultRoles(){
        for(UserRole roleType :  UserRole.values()){
            if(roleRepository.findByRole(roleType).isEmpty()) {
                createRole(roleType);
            }
        }
    }
}