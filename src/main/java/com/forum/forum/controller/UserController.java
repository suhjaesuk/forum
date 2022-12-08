package com.forum.forum.controller;

import com.forum.forum.dto.LoginRequest;
import com.forum.forum.dto.RegisterRequest;
import com.forum.forum.dto.StatusResponse;
import com.forum.forum.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<StatusResponse> register(@Valid @RequestBody RegisterRequest registerRequest){
        StatusResponse user = userService.register(registerRequest);
        return new ResponseEntity<>(user, HttpStatus.valueOf(user.getStatusCode()));
    }

    @PostMapping("/login")
    public ResponseEntity<StatusResponse> login(@RequestBody LoginRequest loginRequest,HttpServletResponse response){
        StatusResponse user = userService.login(loginRequest, response);
        return new ResponseEntity<>(user, HttpStatus.valueOf(user.getStatusCode()));
    }
}
