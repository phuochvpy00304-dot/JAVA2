package lap8.Bai1;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Bài 1: Ứng dụng quản lý dữ liệu số
 * Minh họa Autoboxing/Unboxing và xử lý null an toàn
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Integer> scores = new ArrayList<>();
        
        System.out.println("=== NHẬP ĐIỂM KIỂM TRA ===");
        System.out.println("(Nhập -1 để bỏ qua - giá trị null)");
        
        // Nhập 5 điểm kiểm tra
        for (int i = 1; i <= 5; i++) {
            System.out.print("Nhập điểm sinh viên " + i + ": ");
            int input = scanner.nextInt();
            
            // Autoboxing: int -> Integer
            if (input == -1) {
                scores.add(null); // Thêm null cho sinh viên chưa có điểm
            } else {
                scores.add(input); // Autoboxing xảy ra ở đây
            }
        }
        
        // In danh sách điểm
        System.out.println("\n=== DANH SÁCH ĐIỂM ===");
        for (int i = 0; i < scores.size(); i++) {
            Integer score = scores.get(i);
            if (score == null) {
                System.out.println("Sinh viên " + (i + 1) + ": Chưa có điểm");
            } else {
                // Unboxing: Integer -> int
                System.out.println("Sinh viên " + (i + 1) + ": " + score);
            }
        }
        
        // Tính điểm trung bình (bỏ qua null)
        double average = calculateAverage(scores);
        
        System.out.println("\n=== KẾT QUẢ ===");
        System.out.printf("Điểm trung bình: %.2f\n", average);
        
        // Phân loại
        String classification = classify(average);
        System.out.println("Xếp loại: " + classification);
        
        scanner.close();
    }
    
    /**
     * Tính điểm trung bình, bỏ qua giá trị null
     */
    private static double calculateAverage(ArrayList<Integer> scores) {
        int sum = 0;
        int count = 0;
        
        for (Integer score : scores) {
            if (score != null) { // Xử lý null an toàn
                sum += score; // Unboxing: Integer -> int
                count++;
            }
        }
        
        return count > 0 ? (double) sum / count : 0.0;
    }
    
    /**
     * Phân loại học sinh dựa vào điểm trung bình
     */
    private static String classify(double average) {
        if (average >= 8) {
            return "Giỏi";
        } else if (average >= 6.5) {
            return "Khá";
        } else {
            return "Trung bình";
        }
    }
}
