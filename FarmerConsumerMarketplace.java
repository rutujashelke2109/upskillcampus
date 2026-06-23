
import java.util.*;

class Farmer {

    int farmerId;
    String farmerName;
    double earnings;

    Farmer(int farmerId, String farmerName) {
        this.farmerId = farmerId;
        this.farmerName = farmerName;
        this.earnings = 0;
    }
}

class Product {

    int productId;
    String productName;
    double price;
    int quantity;
    int farmerId;

    Product(int productId, String productName, double price,
            int quantity, int farmerId) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.farmerId = farmerId;
    }
}

class Order {

    int orderId;
    String productName;
    int quantity;
    double totalAmount;

    Order(int orderId, String productName,
            int quantity, double totalAmount) {
        this.orderId = orderId;
        this.productName = productName;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
    }
}

public class FarmerConsumerMarketplace {

    static Scanner sc = new Scanner(System.in);

    static ArrayList<Farmer> farmers = new ArrayList<>();
    static ArrayList<Product> products = new ArrayList<>();
    static ArrayList<Order> orders = new ArrayList<>();

    static int farmerCounter = 1;
    static int productCounter = 1;
    static int orderCounter = 1;

    public static void addFarmer() {

        System.out.print("Enter Farmer Name: ");
        String name = sc.nextLine();

        Farmer farmer
                = new Farmer(farmerCounter++, name);

        farmers.add(farmer);

        System.out.println("Farmer Added Successfully!");
    }

    public static void viewFarmers() {

        if (farmers.isEmpty()) {
            System.out.println("No Farmers Available.");
            return;
        }

        System.out.println("\n------ Farmers ------");

        for (Farmer f : farmers) {
            System.out.println(
                    "ID: " + f.farmerId
                    + " | Name: " + f.farmerName
                    + " | Earnings: ₹" + f.earnings
            );
        }
    }

    public static void addProduct() {

        if (farmers.isEmpty()) {
            System.out.println("Add Farmer First!");
            return;
        }

        viewFarmers();

        System.out.print("Enter Farmer ID: ");
        int farmerId = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Product Name: ");
        String productName = sc.nextLine();

        System.out.print("Enter Price: ");
        double price = sc.nextDouble();

        System.out.print("Enter Quantity: ");
        int quantity = sc.nextInt();

        Product product
                = new Product(
                        productCounter++,
                        productName,
                        price,
                        quantity,
                        farmerId
                );

        products.add(product);

        System.out.println("Product Added Successfully!");
    }

    public static void viewProducts() {

        if (products.isEmpty()) {
            System.out.println("No Products Available.");
            return;
        }

        System.out.println("\n------ Product List ------");

        for (Product p : products) {

            System.out.println(
                    "Product ID: " + p.productId
                    + " | Name: " + p.productName
                    + " | Price: ₹" + p.price
                    + " | Qty: " + p.quantity
                    + " | Farmer ID: " + p.farmerId
            );
        }
    }

    public static void searchProduct() {

        if (products.isEmpty()) {
            System.out.println("No Products Available.");
            return;
        }

        sc.nextLine();

        System.out.print("Enter Product Name: ");
        String search = sc.nextLine();

        boolean found = false;

        for (Product p : products) {

            if (p.productName.equalsIgnoreCase(search)) {

                System.out.println(
                        "Found -> ID: " + p.productId
                        + " Name: " + p.productName
                        + " Price: ₹" + p.price
                        + " Quantity: " + p.quantity
                );

                found = true;
            }
        }

        if (!found) {
            System.out.println("Product Not Found.");
        }
    }

    public static void buyProduct() {

        if (products.isEmpty()) {
            System.out.println("No Products Available.");
            return;
        }

        viewProducts();

        System.out.print("Enter Product ID: ");
        int productId = sc.nextInt();

        System.out.print("Enter Quantity to Buy: ");
        int qty = sc.nextInt();

        Product selectedProduct = null;

        for (Product p : products) {

            if (p.productId == productId) {
                selectedProduct = p;
                break;
            }
        }

        if (selectedProduct == null) {
            System.out.println("Invalid Product ID.");
            return;
        }

        if (selectedProduct.quantity < qty) {
            System.out.println("Insufficient Stock.");
            return;
        }

        selectedProduct.quantity -= qty;

        double total
                = qty * selectedProduct.price;

        for (Farmer f : farmers) {

            if (f.farmerId
                    == selectedProduct.farmerId) {

                f.earnings += total;
            }
        }

        Order order
                = new Order(
                        orderCounter++,
                        selectedProduct.productName,
                        qty,
                        total
                );

        orders.add(order);

        System.out.println(
                "Purchase Successful!"
        );

        System.out.println(
                "Total Amount = ₹" + total
        );
    }

    public static void viewOrders() {

        if (orders.isEmpty()) {
            System.out.println("No Orders Found.");
            return;
        }

        System.out.println(
                "\n------ Order History ------"
        );

        for (Order o : orders) {

            System.out.println(
                    "Order ID: " + o.orderId
                    + " | Product: " + o.productName
                    + " | Qty: " + o.quantity
                    + " | Amount: ₹" + o.totalAmount
            );
        }
    }

    public static void earningsDashboard() {

        if (farmers.isEmpty()) {
            System.out.println("No Farmers Available.");
            return;
        }

        System.out.println(
                "\n------ Earnings Dashboard ------"
        );

        for (Farmer f : farmers) {

            System.out.println(
                    "Farmer ID: " + f.farmerId
                    + " | Name: " + f.farmerName
                    + " | Earnings: ₹" + f.earnings
            );
        }
    }

    public static void updateInventory() {

        if (products.isEmpty()) {
            System.out.println("No Products Available.");
            return;
        }

        viewProducts();

        System.out.print("Enter Product ID: ");
        int productId = sc.nextInt();

        System.out.print("Enter New Quantity: ");
        int newQty = sc.nextInt();

        boolean updated = false;

        for (Product p : products) {

            if (p.productId == productId) {

                p.quantity = newQty;
                updated = true;

                System.out.println(
                        "Inventory Updated Successfully!"
                );
                break;
            }
        }

        if (!updated) {
            System.out.println("Product Not Found.");
        }
    }

    public static void deleteProduct() {

        if (products.isEmpty()) {
            System.out.println("No Products Available.");
            return;
        }

        viewProducts();

        System.out.print("Enter Product ID to Delete: ");
        int id = sc.nextInt();

        boolean removed = false;

        Iterator<Product> iterator
                = products.iterator();

        while (iterator.hasNext()) {

            Product p = iterator.next();

            if (p.productId == id) {

                iterator.remove();
                removed = true;
                break;
            }
        }

        if (removed) {
            System.out.println("Product Deleted.");
        } else {
            System.out.println("Product Not Found.");
        }
    }

    public static void displayMenu() {

        System.out.println("\n==============================");
        System.out.println(" FARMER CONSUMER MARKETPLACE ");
        System.out.println("==============================");

        System.out.println("1. Add Farmer");
        System.out.println("2. View Farmers");
        System.out.println("3. Add Product");
        System.out.println("4. View Products");
        System.out.println("5. Search Product");
        System.out.println("6. Buy Product");
        System.out.println("7. View Orders");
        System.out.println("8. Earnings Dashboard");
        System.out.println("9. Update Inventory");
        System.out.println("10. Delete Product");
        System.out.println("11. Exit");

        System.out.print("Enter Choice: ");
    }

    public static void main(String[] args) {

        int choice;

        do {

            displayMenu();

            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    sc.nextLine();
                    addFarmer();
                    break;

                case 2:
                    viewFarmers();
                    break;

                case 3:
                    sc.nextLine();
                    addProduct();
                    break;

                case 4:
                    viewProducts();
                    break;

                case 5:
                    searchProduct();
                    break;

                case 6:
                    buyProduct();
                    break;

                case 7:
                    viewOrders();
                    break;

                case 8:
                    earningsDashboard();
                    break;

                case 9:
                    updateInventory();
                    break;

                case 10:
                    deleteProduct();
                    break;

                case 11:
                    System.out.println(
                            "Thank You For Using The System!"
                    );
                    break;

                default:
                    System.out.println(
                            "Invalid Choice!"
                    );
            }

        } while (choice != 11);
    }
}
