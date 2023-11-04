package com.anton.sokolov.library.dto;

import lombok.Data;

@Data
public class RegistrationForm {
    private String username;
    private String password;
    private String passwordConfirm;
    private String firstName;
    private String lastName;
    private String email;
}
