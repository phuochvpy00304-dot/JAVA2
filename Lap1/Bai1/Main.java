public class Main {
    public static void main(String[] args) {
        // Tạo danh sách sản phẩm
        Product[] products = new Product[5];
        
        products[0] = new Product("P001", "Laptop Dell", 15000000);
        products[1] = new Product("P002", "Chuột Logitech", 500000);
        products[2] = new ImportedProduct("P003", "iPhone 15 Pro", 25000000, 0.1, 500000);
        products[3] = new Product("P004", "Bàn phím cơ", 1200000);
        products[4] = new ImportedProduct("P005", "MacBook Pro", 35000000, 0.15, 1000000);
        
        // In danh sách sản phẩm và giá cuối cùng
        System.out.println("=== DANH SÁCH SẢN PHẨM ===");
        for (Product product : products) {
            System.out.println(product);
            System.out.printf("Giá cuối cùng: %.0f VNĐ\n", product.finalPrice());
            System.out.println("---");
        }
        
        // Tìm sản phẩm có giá cao nhất
        Product maxPriceProduct = products[0];
        for (Product product : products) {
            if (product.finalPrice() > maxPriceProduct.finalPrice()) {
                maxPriceProduct = product;
            }
        }
        
        System.out.println("\n=== SẢN PHẨM CÓ GIÁ CAO NHẤT ===");
        System.out.println(maxPriceProduct);
        System.out.printf("Giá cuối cùng: %.0f VNĐ\n", maxPriceProduct.finalPrice());
    }
}
