package com.anton.sokolov.library.controller;


import com.anton.sokolov.library.entity.Role;
import com.anton.sokolov.library.entity.User;
import com.anton.sokolov.library.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserRepository userRepository;

    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user-list";
    }

    @GetMapping("{user}")
    public String userEditForm(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("role", Role.values());
        return "user-edit";
    }

    @PostMapping
    public String userSave(
            @RequestParam String username,
            @RequestParam Map<String, String> form,
            @RequestParam("userId") User user) {
        user.setUsername(username);
        Set<String> roles = Arrays.stream(Role.values()).map(Role::name).collect(Collectors.toSet());
        user.getRole().clear();
        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRole().add(Role.valueOf(key));
            }
        }
        userRepository.save(user);
        return "redirect:/user";
    }
}
