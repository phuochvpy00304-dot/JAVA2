public class Product {
    private String id;
    private String name;
    private double basePrice;

    public Product(String id, String name, double basePrice) {
        setId(id);
        this.name = name;
        setBasePrice(basePrice);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID không được rỗng");
        }
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        if (basePrice < 0) {
            throw new IllegalArgumentException("Giá cơ bản phải >= 0");
        }
        this.basePrice = basePrice;
    }

    public double finalPrice() {
        return basePrice;
    }

    @Override
    public String toString() {
        return "Product{id='" + id + "', name='" + name + "', basePrice=" + basePrice + "}";
    }
}
public double finalPrice(){return basePrice;}
@Override
public String toString(){
    reutrn "Products{id='" + id+ "'zzz ,name '" + name + " ', baserice ="+basePrice+"}";}
}