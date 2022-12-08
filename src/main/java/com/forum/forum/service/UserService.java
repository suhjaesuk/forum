package com.forum.forum.service;

import com.forum.forum.dto.LoginRequest;
import com.forum.forum.dto.RegisterRequest;
import com.forum.forum.dto.StatusResponse;
import com.forum.forum.entity.User;
import com.forum.forum.jwt.JwtUtil;
import com.forum.forum.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Transactional
    public StatusResponse register(RegisterRequest registerRequest){
        String username = registerRequest.getUsername();

        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {throw new IllegalArgumentException("중복된 사용자가 존재합니다.");}

        User user = registerRequest.toEntity();
        userRepository.save(user);

        return new StatusResponse("회원가입 완료", HttpStatus.OK.value());
    }

    @Transactional
    public StatusResponse login(LoginRequest loginRequest, HttpServletResponse response){
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("등록된 사용자가 없습니다."));

        if(!user.getPassword().equals(password)){throw  new IllegalArgumentException("비밀번호가 일치하지 않습니다.");}

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(),user.getRole()));

        return new StatusResponse("로그인 완료", HttpStatus.OK.value());
    }
}
