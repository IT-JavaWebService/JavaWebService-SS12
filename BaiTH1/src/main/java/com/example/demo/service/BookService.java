package com.example.demo.service;

import com.example.demo.entity.Book;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class BookService {
    private final List<Book> books = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public BookService() {
        // Data mẫu để test GET All ban đầu
        books.add(new Book(idCounter.getAndIncrement(), "Lập trình Java", "Nguyễn Văn A", 150000.0));
        books.add(new Book(idCounter.getAndIncrement(), "Spring Boot Cơ Bản", "Trần Thị B", 200000.0));
    }

    public List<Book> getAllBooks() {
        return books;
    }

    public Optional<Book> getBookById(Long id) {
        return books.stream().filter(b -> b.getId().equals(id)).findFirst();
    }

    public Book addBook(Book book) {
        book.setId(idCounter.getAndIncrement());
        books.add(book);
        return book;
    }

    public Optional<Book> updateBook(Long id, Book updatedBook) {
        return getBookById(id).map(existingBook -> {
            existingBook.setTitle(updatedBook.getTitle());
            existingBook.setAuthor(updatedBook.getAuthor());
            existingBook.setPrice(updatedBook.getPrice());
            return existingBook;
        });
    }

    public boolean deleteBook(Long id) {
        return books.removeIf(b -> b.getId().equals(id));
    }
}