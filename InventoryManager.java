import java.util.ArrayList;
import java.util.Scanner;

class Product {
    String sku;
    String name;
    int quantity;

    // Constructor
    Product(String sku, String name, int quantity) {
        this.sku = sku;
        this.name = name;
        this.quantity = quantity;
    }
}

public class InventoryManager {

    static ArrayList<Product> inventory = new ArrayList<>();
    static final int MAX_CAPACITY = 100;   // For TC11 (inventory overflow)
    static Scanner sc = new Scanner(System.in);

    // -----------------------------
    // Function to insert product
    // -----------------------------
    static void insertProduct() {
        if (inventory.size() >= MAX_CAPACITY) {
            System.out.println("Inventory capacity exceeded! Cannot insert more products.");
            return;
        }

        System.out.print("Enter SKU: ");
        String sku = sc.nextLine();

        // Check duplicate SKU
        Product found = findBySKU(sku);
        if (found != null) {
            System.out.println("Product with this SKU already exists!");
            System.out.print("Do you want to update its quantity? (yes/no): ");
            String opt = sc.nextLine();

            if (opt.equalsIgnoreCase("yes")) {
                System.out.print("Enter new quantity: ");
                int qty = sc.nextInt();
                sc.nextLine();

                if (qty < 0) {
                    System.out.println("Quantity must be positive!");
                    return;
                }

                found.quantity = qty;
                System.out.println("Quantity updated successfully.");
            }
            return;
        }

        System.out.print("Enter Product Name: ");
        String name = sc.nextLine();

        if (name.trim().isEmpty()) {
            System.out.println("Product name cannot be empty!");
            return;
        }

        System.out.print("Enter Quantity: ");
        int quantity;

        try {
            quantity = Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            System.out.println("Invalid input. Quantity must be numeric.");
            return;
        }

        if (quantity < 0) {
            System.out.println("Quantity must be positive!");
            return;
        }

        Product p = new Product(sku, name, quantity);
        inventory.add(p);

        System.out.println("Product inserted successfully.");
    }

    // -----------------------------
    // Display Inventory
    // -----------------------------
    static void displayInventory() {
        if (inventory.isEmpty()) {
            System.out.println("Inventory is empty.");
            return;
        }

        System.out.println("\nCurrent Inventory:");
        System.out.println("SKU\t\tName\t\tQuantity");
        System.out.println("------------------------------------------");

        for (Product p : inventory) {
            System.out.println(p.sku + "\t\t" + p.name + "\t\t" + p.quantity);
        }
    }

    // -----------------------------
    // Search by SKU
    // -----------------------------
    static Product findBySKU(String sku) {
        for (Product p : inventory) {
            if (p.sku.equals(sku)) return p;
        }
        return null;
    }

    static void searchBySKU() {
        System.out.print("Enter SKU to search: ");
        String sku = sc.nextLine();

        Product p = findBySKU(sku);
        if (p != null) {
            System.out.println("Product Found: " + p.name + " | Qty: " + p.quantity);
        } else {
            System.out.println("Product not found.");
        }
    }

    // -----------------------------
    // Search by Name
    // -----------------------------
    static void searchByName() {
        System.out.print("Enter Product Name: ");
        String name = sc.nextLine();

        boolean found = false;

        for (Product p : inventory) {
            if (p.name.equalsIgnoreCase(name)) {
                System.out.println("SKU: " + p.sku + " | Qty: " + p.quantity);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No product found with this name.");
        }
    }

    // -----------------------------
    // Delete Product
    // -----------------------------
    static void deleteProduct() {
        System.out.print("Enter SKU to delete: ");
        String sku = sc.nextLine();

        Product p = findBySKU(sku);
        if (p != null) {
            inventory.remove(p);
            System.out.println("Product removed successfully.");
        } else {
            System.out.println("Product not found.");
        }
    }

    // -----------------------------
    // Main Menu
    // -----------------------------
    public static void main(String[] args) {

        while (true) {
            System.out.println("\nInventory Stock Manager");
            System.out.println("1. Insert Product");
            System.out.println("2. Display Inventory");
            System.out.println("3. Search by SKU");
            System.out.println("4. Search by Name");
            System.out.println("5. Delete Product");
            System.out.println("6. Exit");

            System.out.print("Enter your choice: ");
            String ch = sc.nextLine();

            switch (ch) {
                case "1": insertProduct(); break;
                case "2": displayInventory(); break;
                case "3": searchBySKU(); break;
                case "4": searchByName(); break;
                case "5": deleteProduct(); break;
                case "6": System.out.println("Exiting..."); return;
                default: System.out.println("Invalid choice!");
            }
        }
    }
}
