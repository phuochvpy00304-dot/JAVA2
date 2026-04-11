package entity;

/**
 * Entity class ánh xạ với bảng student
 */
public class Student {
    private int studentId;
    private String studentName;
    private String gender;
    private double gpa;
    
    // Constructors
    public Student() {
    }
    
    public Student(int studentId, String studentName, String gender, double gpa) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.gender = gender;
        this.gpa = gpa;
    }
    
    public Student(String studentName, String gender, double gpa) {
        this.studentName = studentName;
        this.gender = gender;
        this.gpa = gpa;
    }
    
    // Getters and Setters
    public int getStudentId() {
        return studentId;
    }
    
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
    
    public String getStudentName() {
        return studentName;
    }
    
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
    
    public String getGender() {
        return gender;
    }
    
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    public double getGpa() {
        return gpa;
    }
    
    public void setGpa(double gpa) {
        this.gpa = gpa;
    }
    
    @Override
    public String toString() {
        return String.format("Student[ID=%d, Name=%s, Gender=%s, GPA=%.2f]",
                studentId, studentName, gender, gpa);
    }
}
