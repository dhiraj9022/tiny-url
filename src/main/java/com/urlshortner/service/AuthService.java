package com.urlshortner.service;

import com.urlshortner.dto.LoginDto;
import com.urlshortner.dto.SignInDto;
import com.urlshortner.dto.UserDto;
import com.urlshortner.entity.User;
import com.urlshortner.exception.NotFoundException;
import com.urlshortner.exception.ValidationException;
import com.urlshortner.repository.UserRepo;
import com.urlshortner.service.utils.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private final UserRepo userRepo;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(UserRepo userRepo, JwtUtil jwtUtil, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    public Map<String, Object> signIn(SignInDto regDto) {
        if (userRepo.findByEmailOrUsername(regDto.getEmail(), regDto.getUsername()).isPresent()) {
            throw new ValidationException("username or email already exists");
        }

        User user = new User();
        user.setUsername(regDto.getUsername());
        user.setEmail(regDto.getEmail());
        user.setPassword(passwordEncoder.encode(regDto.getPassword()));
        user.setMobile(regDto.getMobile());
        userRepo.save(user);

        Map<String, Object> map = new HashMap<>();
        map.put("jwt-token", jwtUtil.generateToken(user.getEmail()));
        map.put("user", new UserDto(user.getEmail(), user.getUsername()));
        return map;
    }

    public Map<String, Object> login(LoginDto dto) {
        try {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    dto.getEmail(), dto.getPassword()
            );
            authenticationManager.authenticate(authToken);

            User user = userRepo.findByEmail(dto.getEmail())
                    .orElseThrow(() -> new NotFoundException("user not found"));

            Map<String, Object> map = new HashMap<>();
            map.put("jwt-token", jwtUtil.generateToken(dto.getEmail()));
            map.put("user", new UserDto(user.getEmail(), user.getUsername()));
            return map;
        } catch (AuthenticationException exception) {
            logger.error(exception.getMessage(), exception);
            throw new ValidationException("Invalid Login Credentials");
        }
    }
}