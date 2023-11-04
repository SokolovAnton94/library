package com.anton.sokolov.library.dto;

import lombok.Data;

@Data
public class BookDto {
    private Long id;
    private String title;
    private String ordered;
    private UserDto user;
}
