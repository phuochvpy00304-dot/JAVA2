import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        // Bước 1: Khởi tạo dữ liệu - Tạo danh sách sản phẩm
        List<Product> products = new ArrayList<>();

        products.add(new Product("P001", "Gạo ST25", 25000, ProductCategory.FOOD));
        products.add(new Product("P002", "Sữa tươi", 35000, ProductCategory.FOOD));
        products.add(new Product("P003", "Bánh mì", 15000, ProductCategory.FOOD));
        products.add(new Product("P004", "Laptop Dell", 15000000, ProductCategory.ELECTRONIC));
        products.add(new Product("P005", "iPhone 15", 25000000, ProductCategory.ELECTRONIC));
        products.add(new Product("P006", "Tai nghe", 500000, ProductCategory.ELECTRONIC));
        products.add(new Product("P007", "Áo thun", 150000, ProductCategory.CLOTHING));
        products.add(new Product("P008", "Quần jean", 350000, ProductCategory.CLOTHING));

        // Hiển thị danh sách sản phẩm
        System.out.println("=== DANH SÁCH SẢN PHẨM ===");
        for (Product product : products) {
            System.out.println(product);
        }

        // Bước 2: Thống kê bằng Map
        // Map đếm số lượng sản phẩm theo từng loại
        Map<ProductCategory, Integer> countMap = new HashMap<>();

        // Map tính tổng giá trị sản phẩm theo từng loại
        Map<ProductCategory, Double> totalPriceMap = new HashMap<>();

        // Khởi tạo Map với giá trị ban đầu
        for (ProductCategory category : ProductCategory.values()) {
            countMap.put(category, 0);
            totalPriceMap.put(category, 0.0);
        }

        // Duyệt danh sách sản phẩm và cập nhật Map
        for (Product product : products) {
            ProductCategory category = product.getCategory();

            // Cập nhật số lượng
            countMap.put(category, countMap.get(category) + 1);

            // Cập nhật tổng giá trị
            totalPriceMap.put(category, totalPriceMap.get(category) + product.getPrice());
        }

        // Hiển thị số lượng sản phẩm của mỗi loại
        System.out.println("\n=== SỐ LƯỢNG SẢN PHẨM THEO TỪNG LOẠI ===");
        for (Map.Entry<ProductCategory, Integer> entry : countMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " sản phẩm");
        }

        // Hiển thị tổng giá trị sản phẩm theo từng loại
        System.out.println("\n=== TỔNG GIÁ TRỊ SẢN PHẨM THEO TỪNG LOẠI ===");
        for (Map.Entry<ProductCategory, Double> entry : totalPriceMap.entrySet()) {
            System.out.printf("%s: %.0f VNĐ\n", entry.getKey(), entry.getValue());
        }
    }
}
