package com.anton.sokolov.library.service;


import com.anton.sokolov.library.dto.BookDto;
import com.anton.sokolov.library.entity.Book;
import com.anton.sokolov.library.repository.BookRepository;
import com.anton.sokolov.library.utils.MappingUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final MappingUtils mappingUtils;

    public List<BookDto> findAll() {
        return StreamSupport.stream(bookRepository.findAll().spliterator(), false)
                .map(mappingUtils::mapToBookDto)
                .collect(Collectors.toList());
    }

    public BookDto findById(Long id) {
        return mappingUtils.mapToBookDto(bookRepository.findById(id).orElse(new Book()));
    }

    public void save(BookDto bookDto) {
        Book book = mappingUtils.mapToBook(bookDto);
        bookRepository.save(book);
    }
}

