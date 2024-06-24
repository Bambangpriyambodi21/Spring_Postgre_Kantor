package com.kantor.kantor.service.impl;

import com.kantor.kantor.entity.UserCredential;
import com.kantor.kantor.repository.UserCredentialRepository;
import com.kantor.kantor.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserCredentialRepository userCredentialRepository;


    @Override
    public UserCredential loadByUserId(String userId) {

        return userCredentialRepository.findById(Long.valueOf(userId))
                .orElseThrow(()->new ResponseStatusException(HttpStatus.UNAUTHORIZED, "unauthorized"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userCredentialRepository.findByUsername(username)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.UNAUTHORIZED, "unathorized"));
    }
}
