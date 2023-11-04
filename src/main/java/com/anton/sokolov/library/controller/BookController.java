package com.anton.sokolov.library.controller;


import com.anton.sokolov.library.dto.BookDto;
import com.anton.sokolov.library.dto.OrderDto;
import com.anton.sokolov.library.dto.UserDto;
import com.anton.sokolov.library.service.BookService;
import com.anton.sokolov.library.service.OrderService;
import com.anton.sokolov.library.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * REST-controller for work with books.
 */
@Controller
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;
    private final OrderService orderService;
    private final UserService userService;

    @PostMapping(value = "/user-books", params = "action=take")
    public String takeBook(@Valid BookDto bookDto, Principal principal) {
        BookDto newBook = bookService.findById(bookDto.getId());
        UserDto user = userService.findByUserName(principal.getName());
        if (newBook.getOrdered().equals("Yes")) {
            return "redirect:/user-books";
        }
        bookDto.setOrdered("Yes");
        bookDto.setUser(user);
        bookService.save(bookDto);
        OrderDto orderDto = new OrderDto();
        orderDto.setBook(bookDto);
        orderDto.setUser(user);
        orderService.save(orderDto);
        return "redirect:/user-books";
    }

    @PostMapping(value = "/user-books", params = "action=return")
    public String returnBook(BookDto bookDto) {
        bookDto.setUser(null);
        bookService.save(bookDto);
        return "redirect:/user-books";
    }

    @GetMapping("/user-books")
    public String getAllUserBooks(Principal principal, Model model) {
        List<BookDto> allBooks = bookService.findAll();
        UserDto user = userService.findByUserName(principal.getName());


        List<BookDto> availableBooks =
                allBooks.stream()
                        .filter(book -> book.getUser() == null
                                || (book.getUser().getId().equals(user.getId()) && book.getOrdered().equals("Yes")))
                        .sorted(Comparator.comparing(BookDto::getTitle))
                        .collect(Collectors.toList());

        List<BookDto> usersBooks =
                allBooks.stream()
                        .filter(book -> book.getUser() != null
                                && book.getUser().getId().equals(user.getId())
                                && book.getOrdered().equals("No"))
                        .sorted(Comparator.comparing(BookDto::getTitle))
                        .collect(Collectors.toList());

        String userBook = "user-books";
        model.addAttribute("usersBooks", usersBooks);
        model.addAttribute("availableBooks", availableBooks);
        return userBook;
    }

    @GetMapping("/admin-books")
    public String getAllBooks(Model model) {
        List<BookDto> allBooks = bookService.findAll();

        List<BookDto> availableBooks =
                allBooks.stream()
                        .filter(book -> book.getUser() == null || book.getOrdered().equals("Yes"))
                        .sorted(Comparator.comparing(BookDto::getTitle))
                        .collect(Collectors.toList());

        List<BookDto> usersBooks =
                allBooks.stream()
                        .filter(b -> b.getUser() != null && b.getOrdered().equals("No"))
                        .sorted(Comparator.comparing(BookDto::getTitle))
                        .collect(Collectors.toList());

        model.addAttribute("usersBooks", usersBooks);
        model.addAttribute("availableBooks", availableBooks);
        return "admin-books";
    }
}
