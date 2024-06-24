package com.kantor.kantor.controller;

import com.kantor.kantor.entity.UserCredential;
import com.kantor.kantor.model.request.AuthRequest;
import com.kantor.kantor.model.response.UserResponse;
import com.kantor.kantor.model.response.WebResponse;
import com.kantor.kantor.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping(path = "/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest request){
        UserResponse register = authService.register(request);
        WebResponse<UserResponse> webResponse = WebResponse.<UserResponse>builder()
                .status(HttpStatus.CREATED.getReasonPhrase())
                .message("Succesfully create ner user")
                .Data(register)
                .build();
        return ResponseEntity.ok(webResponse);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request){
        String login = authService.login(request);
        WebResponse<String> webResponse = WebResponse.<String>builder()
                .status(HttpStatus.CREATED.getReasonPhrase())
                .message("Succesfully login")
                .Data(login)
                .build();
        return ResponseEntity.ok(webResponse);
    }
}
