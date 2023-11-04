package com.anton.sokolov.library.controller;

import com.anton.sokolov.library.dto.RegistrationForm;
import com.anton.sokolov.library.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class RegistrationController {
    private final UserServiceImpl userServiceImpl;

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("registrationForm", new RegistrationForm());
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(
            @RequestParam("passwordConfirm") String passwordConfirm,
            @Valid @ModelAttribute("user") RegistrationForm form,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        if (form.getPassword() != null && !form.getPassword().equals(passwordConfirm)) {
            model.addAttribute("passwordConfirm", "Passwords are different");
            return "registration";
        }

        if (!userServiceImpl.createUser(form)) {
            model.addAttribute("registrationForm", form);
            model.addAttribute("registrationError", "Username already exists");
            return "registration";
        }

        return "redirect:/login";
    }
}
