package com.anton.sokolov.library.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username", unique = true)
    @Size(min = 2, max = 20, message = "Must be 2 - 20 symbols")
    private String username;

    @Column(name = "password")
    @Size(min = 2, message = "At least 2 symbols")
    private String password;

    @Transient
    private String passwordConfirm;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> role;

    @Column(name = "first_name")
    @Size(max = 255, message = "Must be less 255 symbols")
    private String firstName;

    @Column(name = "last_name")
    @Size(max = 255, message = "Must be less 255 symbols")
    private String lastName;

    @Email(message = "Email is not correct")
    @NotBlank(message = "Email cannot be empty")
    private String email;
}
