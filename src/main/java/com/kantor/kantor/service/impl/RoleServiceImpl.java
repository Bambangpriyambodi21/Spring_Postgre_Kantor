package com.kantor.kantor.service.impl;

import com.kantor.kantor.constant.ERole;
import com.kantor.kantor.entity.Role;
import com.kantor.kantor.repository.RoleRepository;
import com.kantor.kantor.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;


    @Override
    public Role getOrSave(ERole role) {
        Optional<Role> byRole = roleRepository.findByRole(role);
        if (byRole.isPresent()) return byRole.get();

        Role role1 = Role.builder()
                .role(role)
                .build();
        return roleRepository.save(role1);
    }
}
