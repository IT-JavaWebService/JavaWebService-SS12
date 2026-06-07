package com.example.demo.baith4.exception;

public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(Long id) {
        super("Không tìm thấy sinh viên với ID: " + id);
    }
}