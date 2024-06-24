package com.kantor.kantor.service.impl;

import com.kantor.kantor.constant.ERole;
import com.kantor.kantor.entity.Karyawan;
import com.kantor.kantor.entity.Role;
import com.kantor.kantor.entity.UserCredential;
import com.kantor.kantor.model.request.AuthRequest;
import com.kantor.kantor.model.request.KaryawanRequest;
import com.kantor.kantor.model.response.UserResponse;
import com.kantor.kantor.repository.UserCredentialRepository;
import com.kantor.kantor.security.JWTUtils;
import com.kantor.kantor.service.AuthService;
import com.kantor.kantor.service.KaryawanService;
import com.kantor.kantor.service.RoleService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserCredentialRepository userCredentialRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final JWTUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final KaryawanService karyawanService;

    @PostConstruct
    public void initSuperAdmin(){
        Optional<UserCredential> byUsername = userCredentialRepository.findByUsername("superadmin@gmail.com");
        if (byUsername.isPresent()) return;

        Role orSaveSA = roleService.getOrSave(ERole.ROLE_SUPER_ADMIN);
        Role orSaveK = roleService.getOrSave(ERole.ROLE_KARYAWAN);

        String password = passwordEncoder.encode("password");

        UserCredential userCredential = UserCredential.builder()
                .username("superadmin@gmail.com")
                .password(password)
                .roles(List.of(orSaveK, orSaveSA))
                .build();
        userCredentialRepository.saveAndFlush(userCredential);
    }


    @Override
    public UserResponse register(AuthRequest request) {
        Role role = roleService.getOrSave(ERole.ROLE_KARYAWAN);

        String password = passwordEncoder.encode(request.getPassword());

        UserCredential userCredential = UserCredential.builder()
                .username(request.getUsername())
                .password(password)
                .roles(List.of(role))
                .build();
        userCredentialRepository.saveAndFlush(userCredential);

        KaryawanRequest karyawan = KaryawanRequest.builder()
                .id_user(userCredential.getId().toString())
                .build();
        karyawanService.create(karyawan);
        List<String> list = userCredential.getRoles().stream().map(role1 -> role1.getRole().name()).toList();
        UserResponse userResponse = UserResponse.builder()
                .username(userCredential.getUsername())
                .roles(list)
                .build();
        return userResponse;
    }

    @Override
    public String login(AuthRequest request) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        Authentication authentication1 = authenticationManager.authenticate(authentication);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserCredential principal = (UserCredential) authentication1.getPrincipal();
        return jwtUtils.generationToken(principal);
    }
}
