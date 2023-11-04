package com.anton.sokolov.library.dto;

import lombok.Data;

@Data
public class OrderDto {
    private Long id;
    private UserDto user;
    private BookDto book;
}
