package supermarket;
public class Product {
    String category;
    String name;
    double price;
    public Product(String category, String name, double price) {
        this.category = category;
        this.name = name;
        this.price = price;
    }
    public void displayProduct() {
        System.out.printf("%s - Rs. %.2f/kg%n", name, price);
    }
}