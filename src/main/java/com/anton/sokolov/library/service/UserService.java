package com.anton.sokolov.library.service;


import com.anton.sokolov.library.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDto findByUserName(String username);

}
