package com.example.demo.baith4.repository;


import com.example.demo.baith4.model.Student;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class StudentRepository {
    private final List<Student> studentList = new ArrayList<>();
    private Long autoId = 1L;

    public StudentRepository() {
        // Thêm sẵn dữ liệu mẫu để test
        studentList.add(new Student(autoId++, "SV001", "Nguyễn Văn A", "CNTT", 3.5));
        studentList.add(new Student(autoId++, "SV002", "Trần Thị B", "Kinh Tế", 3.2));
    }

    public List<Student> findAll() { return studentList; }

    public Optional<Student> findById(Long id) {
        return studentList.stream().filter(s -> s.getId().equals(id)).findFirst();
    }

    public Student save(Student student) {
        if (student.getId() == null) {
            student.setId(autoId++);
            studentList.add(student);
        } else {
            findById(student.getId()).ifPresent(s -> {
                s.setStudentCode(student.getStudentCode());
                s.setFullName(student.getFullName());
                s.setMajor(student.getMajor());
                s.setGpa(student.getGpa());
            });
        }
        return student;
    }

    public boolean deleteById(Long id) {
        return studentList.removeIf(s -> s.getId().equals(id));
    }
}