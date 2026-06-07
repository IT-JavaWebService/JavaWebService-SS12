package com.example.demo.baith4.model;

public class Student {
    private Long id;
    private String studentCode;
    private String fullName;
    private String major;
    private Double gpa;

    public Student() {}

    public Student(Long id, String studentCode, String fullName, String major, Double gpa) {
        this.id = id;
        this.studentCode = studentCode;
        this.fullName = fullName;
        this.major = major;
        this.gpa = gpa;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getStudentCode() { return studentCode; }
    public void setStudentCode(String studentCode) { this.studentCode = studentCode; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getMajor() { return major; }
    public void setMajor(String major) { this.major = major; }

    public Double getGpa() { return gpa; }
    public void setGpa(Double gpa) { this.gpa = gpa; }
}