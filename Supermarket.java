package supermarket;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
public class Supermarket {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Product> products = new ArrayList<>();
        try (BufferedReader br =
                new BufferedReader(new FileReader("products.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                String[] data = line.split(",", -1);
                if (data.length != 3
                        || data[0].trim().isEmpty()
                        || data[1].trim().isEmpty()) {
                    System.out.println("Skipping invalid line: " + line);
                    continue;
                }
                try {
                    String category = data[0].trim();
                    String name = data[1].trim();
                    double price = Double.parseDouble(data[2].trim());
                    if (price < 0 || !Double.isFinite(price)) {
                        System.out.println("Skipping invalid price: " + line);
                        continue;
                    }
                    products.add(new Product(category, name, price));
                } catch (NumberFormatException e) {
                    System.out.println("Skipping invalid price: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading products.txt");
            System.out.println("Keep products.txt in the UPS project folder.");
            sc.close();
            return;
        }
        if (products.isEmpty()) {
            System.out.println("No valid products found in the file.");
            sc.close();
            return;
        }
        System.out.println("Products loaded: " + products.size());
        double total = 0;
        int choice;
        int productChoice;
        double kg;
        do {
            System.out.println("\n===== SUPERMARKET =====");
            System.out.println("1. Vegetables");
            System.out.println("2. Fruits");
            System.out.println("3. Rice");
            System.out.println("4. Meat");
            System.out.println("5. Exit and Generate Bill");
            System.out.print("Enter your choice: ");
            if (!sc.hasNextInt()) {
                System.out.println("Please enter a number.");
                sc.next();
                continue;
            }
            choice = sc.nextInt();
            String category = "";
            switch (choice) {
                case 1:
                    category = "Vegetables";
                    break;
                case 2:
                    category = "Fruits";
                    break;
                case 3:
                    category = "Rice";
                    break;
                case 4:
                    category = "Meat";
                    break;
                case 5:
                    System.out.println("\n===== FINAL BILL =====");
                    System.out.printf("Grand Total: Rs. %.2f%n", total);
                    System.out.println("Thank you for shopping!");
                    break;
                default:
                    System.out.println("Invalid choice!");
                    continue;
            }
            if (choice == 5) {
                break;
            }
            ArrayList<Product> available = new ArrayList<>();
            for (Product p : products) {
                if (p.category.equalsIgnoreCase(category)) {
                    available.add(p);
                }
            }
            if (available.isEmpty()) {
                System.out.println("No products available in this category.");
                continue;
            }
            System.out.println("\n--- " + category + " ---");
            for (int i = 0; i < available.size(); i++) {
                System.out.print((i + 1) + ". ");
                available.get(i).displayProduct();
            }
            System.out.print("Select product: ");
            if (!sc.hasNextInt()) {
                System.out.println("Please enter a valid product number.");
                sc.next();
                continue;
            }
            productChoice = sc.nextInt();
            if (productChoice < 1 || productChoice > available.size()) {
                System.out.println("Invalid product!");
                continue;
            }
            Product selected = available.get(productChoice - 1);
            System.out.print("Enter quantity in kg: ");
            if (!sc.hasNextDouble()) {
                System.out.println("Please enter a valid quantity.");
                sc.next();
                continue;
            }
            kg = sc.nextDouble();
            if (kg <= 0 || !Double.isFinite(kg)) {
                System.out.println("Quantity must be greater than zero.");
                continue;
            }
            double amount = selected.price * kg;
            total += amount;
            System.out.println("\nProduct: " + selected.name);
            System.out.printf("Quantity: %.2f kg%n", kg);
            System.out.printf("Item Total: Rs. %.2f%n", amount);
            System.out.printf("Running Total: Rs. %.2f%n", total);
        } while (true);
        sc.close();
    }
}