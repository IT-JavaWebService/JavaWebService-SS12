package com.example.demo.controller;

import com.example.demo.entity.Book;
import com.example.demo.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        return bookService.getBookById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        // Validate đơn giản để trả về lỗi 400 Bad Request nếu thiếu thông tin bắt buộc
        if (book.getTitle() == null || book.getTitle().isEmpty() || book.getPrice() == null || book.getPrice() <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Book createdBook = bookService.addBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book) {
        if (book.getTitle() == null || book.getTitle().isEmpty() || book.getPrice() == null || book.getPrice() <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return bookService.updateBook(id, book)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        boolean isDeleted = bookService.deleteBook(id);
        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // 204
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404
    }
}