package Lap5.Bai3;

import java.io.Serializable;

/**
 * Lớp Student - Model cho Bài 3
 */
public class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String id;
    private String name;
    private double gpa;

    public Student() {
    }

    public Student(String id, String name, double gpa) {
        this.id = id;
        this.name = name;
        this.gpa = gpa;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    @Override
    public String toString() {
        return String.format("Student[ID=%s, Name=%s, GPA=%.2f]", id, name, gpa);
    }
}
