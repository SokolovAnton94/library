package com.anton.sokolov.library.service.impl;

import com.anton.sokolov.library.dto.RegistrationForm;
import com.anton.sokolov.library.dto.UserDto;
import com.anton.sokolov.library.entity.Role;
import com.anton.sokolov.library.entity.User;
import com.anton.sokolov.library.repository.UserRepository;
import com.anton.sokolov.library.service.UserService;
import com.anton.sokolov.library.utils.MappingUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MappingUtils mappingUtils;

    @Override
    @Transactional(readOnly = true)
    public UserDto findByUserName(String username) {
        User user = userRepository.findByUsername(username);
        return mappingUtils.mapToUserDto(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getRole());
    }

    public boolean createUser(RegistrationForm form) {
        User userFromDb = userRepository.findByUsername(form.getUsername());
        if (userFromDb != null) {
            return false;
        }

        User user = new User();
        user.setUsername(form.getUsername());
        user.setFirstName(form.getFirstName());
        user.setLastName(form.getLastName());
        user.setEmail(form.getEmail());
        user.setRole(Collections.singleton(Role.USER));
        user.setPassword(passwordEncoder.encode(form.getPassword()));
        userRepository.save(user);
        return true;
    }
}
