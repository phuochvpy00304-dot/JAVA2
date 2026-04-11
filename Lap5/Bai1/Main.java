package Lap5.Bai1;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Bài 1: Đọc và ghi file văn bản bằng Character Stream
 */
public class Main {
    private static final String FILE_NAME = "Lap5/Bai1/students.txt";

    public static void main(String[] args) {
        // Danh sách tên sinh viên
        List<String> students = new ArrayList<>();
        students.add("Nguyen Van A");
        students.add("Tran Thi B");
        students.add("Le Van C");
        students.add("Pham Thi D");
        students.add("Hoang Van E");

        // Ghi dữ liệu vào file
        writeToFile(students);

        // Đọc dữ liệu từ file
        readFromFile();
    }

    /**
     * Ghi danh sách sinh viên vào file văn bản
     */
    public static void writeToFile(List<String> students) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            System.out.println("=== GHI DU LIEU VAO FILE ===");
            for (String student : students) {
                writer.write(student);
                writer.newLine();
                System.out.println("Da ghi: " + student);
            }
            System.out.println("Ghi file thanh cong!\n");
        } catch (IOException e) {
            System.err.println("Loi khi ghi file: " + e.getMessage());
        }
    }

    /**
     * Đọc danh sách sinh viên từ file văn bản
     */
    public static void readFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            System.out.println("=== DOC DU LIEU TU FILE ===");
            String line;
            int count = 1;
            while ((line = reader.readLine()) != null) {
                System.out.println(count + ". " + line);
                count++;
            }
            System.out.println("Doc file thanh cong!");
        } catch (IOException e) {
            System.err.println("Loi khi doc file: " + e.getMessage());
        }
    }
}
