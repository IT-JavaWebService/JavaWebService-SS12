package com.example.demo.baith4.service;


import com.example.demo.baith4.exception.StudentNotFoundException;
import com.example.demo.baith4.model.Student;
import com.example.demo.baith4.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents() { return studentRepository.findAll(); }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id)); // Ném Custom Exception ở đây
    }

    public Student addStudent(Student student) { return studentRepository.save(student); }

    public Student updateStudent(Long id, Student updatedData) {
        Student existing = getStudentById(id);
        updatedData.setId(existing.getId());
        return studentRepository.save(updatedData);
    }

    public void deleteStudent(Long id) {
        Student existing = getStudentById(id);
        studentRepository.deleteById(existing.getId());
    }
}