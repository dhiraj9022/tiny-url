package com.urlshortner.service;

import com.urlshortner.dto.UserDto;
import com.urlshortner.exception.NotFoundException;
import com.urlshortner.repository.UserRepo;
import com.urlshortner.service.utils.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AuthUtil authUtil;

    public UserDto getAuthUser() {
        Optional<UserDto> userDto = userRepo.findDtoByEmail(authUtil.getAuthEmail());
        if (!userDto.isPresent()) throw new NotFoundException("user not found");
        return userDto.get();
    }
}
