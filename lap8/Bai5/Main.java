package lap8.Bai5;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Bài 5: Tổng hợp - Sử dụng AI để cải thiện code
 * Chương trình quản lý điểm sinh viên với:
 * - Autoboxing/Unboxing
 * - Xử lý null an toàn
 * - Text Block để hiển thị kết quả
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Integer> scores = new ArrayList<>();
        
        // Banner chào mừng
        String banner = """
                ╔═══════════════════════════════════════════╗
                ║  CHƯƠNG TRÌNH QUẢN LÝ ĐIỂM SINH VIÊN     ║
                ║  Sử dụng: Autoboxing, Text Block         ║
                ╚═══════════════════════════════════════════╝
                """;
        System.out.println(banner);
        
        // Nhập điểm
        System.out.println("Nhập điểm cho 5 sinh viên (nhập -1 nếu chưa có điểm):\n");
        for (int i = 1; i <= 5; i++) {
            System.out.print("Sinh viên " + i + ": ");
            int input = scanner.nextInt();
            
            // Autoboxing: int -> Integer
            // Xử lý null an toàn
            if (input == -1) {
                scores.add(null);
            } else if (input < 0 || input > 10) {
                System.out.println("⚠ Điểm không hợp lệ! Đặt là null.");
                scores.add(null);
            } else {
                scores.add(input); // Autoboxing xảy ra
            }
        }
        
        // Tính toán
        ScoreAnalysis analysis = analyzeScores(scores);
        
        // Hiển thị kết quả bằng Text Block
        displayResults(scores, analysis);
        
        scanner.close();
    }
    
    /**
     * Phân tích điểm - xử lý null an toàn
     */
    private static ScoreAnalysis analyzeScores(ArrayList<Integer> scores) {
        int sum = 0;
        int count = 0;
        int nullCount = 0;
        
        for (Integer score : scores) {
            if (score != null) {
                // Unboxing an toàn: Integer -> int
                sum += score;
                count++;
            } else {
                nullCount++;
            }
        }
        
        double average = count > 0 ? (double) sum / count : 0.0;
        String classification = classifyScore(average);
        
        return new ScoreAnalysis(sum, count, nullCount, average, classification);
    }
    
    /**
     * Phân loại điểm
     */
    private static String classifyScore(double average) {
        if (average >= 8) return "Giỏi";
        if (average >= 6.5) return "Khá";
        return "Trung bình";
    }
    
    /**
     * Hiển thị kết quả bằng Text Block với .formatted()
     */
    private static void displayResults(ArrayList<Integer> scores, ScoreAnalysis analysis) {
        // Tạo danh sách điểm
        StringBuilder scoreList = new StringBuilder();
        for (int i = 0; i < scores.size(); i++) {
            Integer score = scores.get(i);
            if (score != null) {
                scoreList.append(String.format("  │ Sinh viên %d: %2d điểm\n", i + 1, score));
            } else {
                scoreList.append(String.format("  │ Sinh viên %d: Chưa có điểm\n", i + 1));
            }
        }
        
        // Text Block với .formatted()
        String report = """
                
                ╔═══════════════════════════════════════════╗
                ║           KẾT QUẢ PHÂN TÍCH ĐIỂM          ║
                ╠═══════════════════════════════════════════╣
                ║ DANH SÁCH ĐIỂM:                           ║
                %s╠═══════════════════════════════════════════╣
                ║ THỐNG KÊ:                                 ║
                ║  • Tổng số sinh viên: %d                  ║
                ║  • Đã có điểm: %d sinh viên               ║
                ║  • Chưa có điểm: %d sinh viên             ║
                ║  • Tổng điểm: %d                          ║
                ║  • Điểm trung bình: %.2f                  ║
                ║  • Xếp loại: %-28s ║
                ╚═══════════════════════════════════════════╝
                
                💡 LƯU Ý VỀ AUTOBOXING/UNBOXING:
                ─────────────────────────────────────────────
                • Autoboxing: Tự động chuyển int → Integer
                  Ví dụ: scores.add(8) → Integer(8)
                
                • Unboxing: Tự động chuyển Integer → int
                  Ví dụ: sum += score → sum += score.intValue()
                
                • Nguy cơ NullPointerException:
                  - Khi unboxing giá trị null → Lỗi!
                  - Giải pháp: Kiểm tra null trước khi sử dụng
                    if (score != null) { sum += score; }
                
                ✓ Code này đã xử lý null an toàn!
                """.formatted(
                    scoreList.toString(),
                    scores.size(),
                    analysis.count,
                    analysis.nullCount,
                    analysis.sum,
                    analysis.average,
                    analysis.classification
                );
        
        System.out.println(report);
    }
    
    /**
     * Record để lưu kết quả phân tích
     */
    private record ScoreAnalysis(
        int sum,
        int count,
        int nullCount,
        double average,
        String classification
    ) {}
}
