package lap8.Bai4;

/**
 * Bài 4: Text Block - Quản lý văn bản nhiều dòng
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== BÀI 4: TEXT BLOCK ===\n");
        
        // 1. Thông báo chào mừng nhiều dòng
        String welcomeMessage = """
                ╔════════════════════════════════════════╗
                ║   CHÀO MỪNG ĐẾN VỚI HỆ THỐNG QUẢN LÝ  ║
                ║         SINH VIÊN JAVA 2026           ║
                ╠════════════════════════════════════════╣
                ║  • Quản lý thông tin sinh viên        ║
                ║  • Tính điểm trung bình               ║
                ║  • Xét học bổng                       ║
                ║  • Báo cáo thống kê                   ║
                ╚════════════════════════════════════════╝
                """;
        
        System.out.println("1. Thông báo chào mừng:");
        System.out.println(welcomeMessage);
        
        // 2. Email với .formatted()
        String recipientName = "Nguyễn Văn A";
        String studentId = "SV001";
        double gpa = 3.75;
        
        String emailContent = """
                Kính gửi: %s
                
                Chúng tôi xin thông báo kết quả học tập của bạn:
                
                Mã sinh viên: %s
                Điểm trung bình (GPA): %.2f
                
                Chúc mừng! Bạn đã đạt được kết quả xuất sắc và đủ điều kiện
                nhận học bổng học kỳ này.
                
                Vui lòng liên hệ phòng Đào tạo để hoàn tất thủ tục.
                
                Trân trọng,
                Phòng Đào tạo
                """.formatted(recipientName, studentId, gpa);
        
        System.out.println("\n2. Email thông báo:");
        System.out.println(emailContent);
        
        // 3. HTML với dữ liệu sinh viên
        String htmlReport = """
                <!DOCTYPE html>
                <html>
                <head>
                    <title>Báo cáo sinh viên</title>
                    <style>
                        table { border-collapse: collapse; width: 100%%; }
                        th, td { border: 1px solid black; padding: 8px; text-align: left; }
                        th { background-color: #4CAF50; color: white; }
                        .scholarship { background-color: #ffeb3b; }
                    </style>
                </head>
                <body>
                    <h1>Báo cáo kết quả học tập</h1>
                    <table>
                        <tr>
                            <th>Mã SV</th>
                            <th>Họ tên</th>
                            <th>GPA</th>
                            <th>Học bổng</th>
                        </tr>
                        <tr class="scholarship">
                            <td>%s</td>
                            <td>%s</td>
                            <td>%.2f</td>
                            <td>Có</td>
                        </tr>
                        <tr>
                            <td>SV002</td>
                            <td>Trần Thị B</td>
                            <td>2.85</td>
                            <td>Không</td>
                        </tr>
                        <tr class="scholarship">
                            <td>SV003</td>
                            <td>Lê Văn C</td>
                            <td>3.50</td>
                            <td>Có</td>
                        </tr>
                    </table>
                </body>
                </html>
                """.formatted(studentId, recipientName, gpa);
        
        System.out.println("\n3. HTML Report:");
        System.out.println(htmlReport);
        
        // 4. SQL Query với Text Block
        double minGpa = 3.0;
        String sqlQuery = """
                SELECT 
                    student_id,
                    student_name,
                    gpa,
                    CASE 
                        WHEN gpa >= 3.5 THEN 'Xuất sắc'
                        WHEN gpa >= 3.2 THEN 'Giỏi'
                        WHEN gpa >= 2.5 THEN 'Khá'
                        ELSE 'Trung bình'
                    END AS classification
                FROM students
                WHERE gpa > %.1f
                ORDER BY gpa DESC;
                """.formatted(minGpa);
        
        System.out.println("\n4. SQL Query:");
        System.out.println(sqlQuery);
        
        // 5. So sánh với cách cũ (không dùng Text Block)
        System.out.println("\n5. So sánh với cách cũ:");
        String oldWay = "SELECT student_id, student_name, gpa\n" +
                       "FROM students\n" +
                       "WHERE gpa > " + minGpa + "\n" +
                       "ORDER BY gpa DESC;";
        
        System.out.println("Cách cũ (khó đọc, khó bảo trì):");
        System.out.println(oldWay);
        
        System.out.println("\n✓ Text Block giúp code dễ đọc, dễ bảo trì hơn!");
    }
}
