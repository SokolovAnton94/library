package com.anton.sokolov.library.controller;


import com.anton.sokolov.library.dto.BookDto;
import com.anton.sokolov.library.dto.OrderDto;
import com.anton.sokolov.library.service.BookService;
import com.anton.sokolov.library.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

/**
 * REST-controller for work with books.
 */
@Controller
@RequiredArgsConstructor
public class OrderController {
    private final BookService bookService;
    private final OrderService orderService;

    @PostMapping(value = "/orders", params = "action=approve")
    public String approveBook(@Valid OrderDto orderDto) {
        BookDto bookDto = bookService.findById(orderDto.getBook().getId());
        bookDto.setOrdered("No");
        bookService.save(bookDto);
        orderService.deleteById(orderDto.getId());
        return "redirect:/orders";
    }

    @PostMapping(value = "/orders", params = "action=decline")
    public String declineBook(@Valid OrderDto orderDto) {
        BookDto bookDto = bookService.findById(orderDto.getBook().getId());
        bookDto.setOrdered("No");
        bookDto.setUser(null);
        bookService.save(bookDto);
        orderService.deleteById(orderDto.getId());
        return "redirect:/orders";
    }

    /**
     * Method get all books from DB.
     */
    @GetMapping("/orders")
    public String getAllBooks(Model model) {
        List<OrderDto> allOrders = orderService.findAll();
        model.addAttribute("orders", allOrders);
        return "orders";
    }
}
