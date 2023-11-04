package com.anton.sokolov.library.utils;


import com.anton.sokolov.library.dto.BookDto;
import com.anton.sokolov.library.dto.OrderDto;
import com.anton.sokolov.library.dto.UserDto;
import com.anton.sokolov.library.entity.Book;
import com.anton.sokolov.library.entity.Order;
import com.anton.sokolov.library.entity.User;
import org.springframework.stereotype.Service;

@Service
public class MappingUtils {
    public BookDto mapToBookDto(Book entity) {
        BookDto dto = new BookDto();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setUser(mapToUserDto(entity.getUser()));
        dto.setOrdered(entity.isOrder() ? "Yes" : "No");
        return dto;
    }

    public Book mapToBook(BookDto dto) {
        Book entity = new Book();
        entity.setId(dto.getId());
        entity.setTitle(dto.getTitle());
        entity.setUser(mapToUser(dto.getUser()));
        entity.setOrder(dto.getOrdered().equals("Yes"));
        return entity;
    }

    public OrderDto mapToOrderDto(Order entity) {
        OrderDto dto = new OrderDto();
        dto.setId(entity.getId());
        dto.setBook(mapToBookDto(entity.getBook()));
        dto.setUser(mapToUserDto(entity.getUser()));
        return dto;
    }

    public Order mapToOrder(OrderDto dto) {
        Order entity = new Order();
        entity.setId(dto.getId());
        entity.setBook(mapToBook(dto.getBook()));
        entity.setUser(mapToUser(dto.getUser()));
        return entity;
    }

    public UserDto mapToUserDto(User entity) {
        if (entity == null) {
            return null;
        }
        return UserDto.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .email(entity.getEmail())
                .build();
    }

    public User mapToUser(UserDto dto) {
        if (dto == null) {
            return null;
        }
        User user = new User();
        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        return user;
    }
}
