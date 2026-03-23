import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Bài 6: Sử dụng AI để sinh ra code mẫu và fix lỗi liên quan đến Stream API & Lambda
 * 
 * Dưới đây là 5 đoạn code có lỗi và cách sửa chúng:
 */
public class Bai6 {
    
    // ===== LỖI 1: Sử dụng forEach với return =====
    // CODE LỖI:
    /*
    public static void printEvenNumbers(List<Integer> numbers) {
        numbers.stream()
               .forEach(n -> {
                   if (n % 2 == 0) {
                       System.out.println(n);
                   } else {
                       return; // LỖI: không thể dùng return trong forEach
                   }
               });
    }
    */
    
    // CODE SỬA:
    // Giải thích: forEach không hỗ trợ return để bỏ qua phần tử.
    // Nên dùng filter trước khi forEach.
    public static void printEvenNumbers(List<Integer> numbers) {
        numbers.stream()
               .filter(n -> n % 2 == 0)
               .forEach(System.out::println);
    }
    
    
    // ===== LỖI 2: Thay đổi biến bên ngoài trong lambda =====
    // CODE LỖI:
    /*
    public static int sumNumbers(List<Integer> numbers) {
        int sum = 0;
        numbers.stream()
               .forEach(n -> sum += n); // LỖI: biến sum phải là final hoặc effectively final
        return sum;
    }
    */
    
    // CODE SỬA:
    // Giải thích: Lambda không thể thay đổi biến local bên ngoài.
    // Nên dùng reduce hoặc mapToInt().sum()
    public static int sumNumbers(List<Integer> numbers) {
        return numbers.stream()
                      .mapToInt(Integer::intValue)
                      .sum();
    }
    
    
    // ===== LỖI 3: Sử dụng collect() sai cách =====
    // CODE LỖI:
    /*
    public static List<String> getUpperCaseNames(List<String> names) {
        return names.stream()
                    .map(String::toUpperCase)
                    .collect(); // LỖI: thiếu Collector
    }
    */
    
    // CODE SỬA:
    // Giải thích: collect() cần một Collector làm tham số.
    // Dùng Collectors.toList() để thu thập thành List.
    public static List<String> getUpperCaseNames(List<String> names) {
        return names.stream()
                    .map(String::toUpperCase)
                    .collect(Collectors.toList());
    }
    
    
    // ===== LỖI 4: Sử dụng stream nhiều lần =====
    // CODE LỖI:
    /*
    public static void processNumbers(List<Integer> numbers) {
        var stream = numbers.stream();
        long count = stream.count();
        System.out.println("Count: " + count);
        
        int sum = stream.mapToInt(Integer::intValue).sum(); // LỖI: stream đã bị đóng
        System.out.println("Sum: " + sum);
    }
    */
    
    // CODE SỬA:
    // Giải thích: Stream chỉ có thể sử dụng một lần. Sau khi terminal operation
    // (count, sum, collect...) được gọi, stream bị đóng.
    // Phải tạo stream mới cho mỗi operation.
    public static void processNumbers(List<Integer> numbers) {
        long count = numbers.stream().count();
        System.out.println("Count: " + count);
        
        int sum = numbers.stream().mapToInt(Integer::intValue).sum();
        System.out.println("Sum: " + sum);
    }
    
    
    // ===== LỖI 5: Sử dụng filter với kiểu trả về sai =====
    // CODE LỖI:
    /*
    public static List<Integer> filterPositive(List<Integer> numbers) {
        return numbers.stream()
                      .filter(n -> {
                          if (n > 0) {
                              return n; // LỖI: filter cần trả về boolean, không phải giá trị
                          }
                          return null;
                      })
                      .collect(Collectors.toList());
    }
    */
    
    // CODE SỬA:
    // Giải thích: filter() nhận Predicate (trả về boolean), không phải Function.
    // Lambda trong filter phải trả về true/false để quyết định giữ hay loại bỏ phần tử.
    public static List<Integer> filterPositive(List<Integer> numbers) {
        return numbers.stream()
                      .filter(n -> n > 0)
                      .collect(Collectors.toList());
    }
    
    
    // ===== MAIN: Test các hàm đã sửa =====
    public static void main(String[] args) {
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<String> names = List.of("an", "binh", "cuong", "dung");
        
        System.out.println("=== Test Lỗi 1: Print Even Numbers ===");
        printEvenNumbers(numbers);
        
        System.out.println("\n=== Test Lỗi 2: Sum Numbers ===");
        System.out.println("Sum: " + sumNumbers(numbers));
        
        System.out.println("\n=== Test Lỗi 3: Get UpperCase Names ===");
        System.out.println(getUpperCaseNames(names));
        
        System.out.println("\n=== Test Lỗi 4: Process Numbers ===");
        processNumbers(numbers);
        
        System.out.println("\n=== Test Lỗi 5: Filter Positive ===");
        System.out.println(filterPositive(List.of(-5, -2, 0, 3, 7, -1, 10)));
    }
}
