package com.anton.sokolov.library.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "is_order")
    private boolean order;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
