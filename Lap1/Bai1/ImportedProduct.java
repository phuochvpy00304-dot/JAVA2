public class ImportedProduct extends Product {
    private double importTaxRate;
    private double shippingFee;

    public ImportedProduct(String id, String name, double basePrice, double importTaxRate, double shippingFee) {
        super(id, name, basePrice);
        setImportTaxRate(importTaxRate);
        setShippingFee(shippingFee);
    }

    public double getImportTaxRate() {
        return importTaxRate;
    }

    public void setImportTaxRate(double importTaxRate) {
        if (importTaxRate < 0 || importTaxRate > 1) {
            throw new IllegalArgumentException("Thuế nhập khẩu phải từ 0 đến 1");
        }
        this.importTaxRate = importTaxRate;
    }

    public double getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(double shippingFee) {
        if (shippingFee < 0) {
            throw new IllegalArgumentException("Phí vận chuyển phải >= 0");
        }
        this.shippingFee = shippingFee;
    }

    @Override
    public double finalPrice() {
        return getBasePrice() + getBasePrice() * importTaxRate + shippingFee;
    }

    @Override
    public String toString() {
        return "ImportedProduct{id='" + getId() + "', name='" + getName() + 
               "', basePrice=" + getBasePrice() + ", importTaxRate=" + importTaxRate + 
               ", shippingFee=" + shippingFee + "}";
    }
}
