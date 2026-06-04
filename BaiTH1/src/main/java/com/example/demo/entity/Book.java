package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    private Long id;
    private String title;
    private String author;
    private Double price;


    @Override
    public String toString() {
        return "Book{" + "id=" + id + ", title='" + title + '\'' + ", author='" + author + '\'' + ", price=" + price + '}';
    }
}