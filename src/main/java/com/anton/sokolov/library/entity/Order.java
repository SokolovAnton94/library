package com.anton.sokolov.library.entity;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne()
    @JoinColumn(name = "book_id")
    private Book book;
}
