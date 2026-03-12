import java.util.*;

class Product {
    private int id;
    private String name;
    private double price;
    private int quantity;
    private String category;

    public Product() {
    }

    public Product(int id, String name, double price, int quantity, String category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}

class InvalidProductException extends Exception {
    public InvalidProductException(String message) {
        super(message);
    }
}

class ProductManager {
    private ArrayList<Product> products;

    public ProductManager() {
        products = new ArrayList<>();
    }

    public void addProduct(Product product) throws InvalidProductException {
        boolean exists = products.stream().anyMatch(p -> p.getId() == product.getId());
        if (exists) {
            throw new InvalidProductException("Id da ton tai trong danh sach vui long nhap id khac!");
        }
        products.add(product);
    }

    public void displayProducts() {
        if (products.isEmpty()) {
            System.out.println("Danh sach rong !");
            return;
        }

        System.out.printf("%-5s %-20s %-10s %-10s %-15s\n", "ID", "Ten", "Gia", "SoLuong", "DanhMuc");
        products.forEach(p -> System.out.printf("%-5d %-20s %-10.2f %-10d %-15s\n", p.getId(), p.getName(),
                p.getPrice(), p.getQuantity(), p.getCategory()));
    }

    public void updateQuantity(int id, int newQuantity) throws InvalidProductException {
        Optional<Product> productOpt = products.stream().filter(p -> p.getId() == id).findFirst();
        Product product = productOpt.orElseThrow(() -> new InvalidProductException("Id khong ton tai trong kho !"));
        product.setQuantity(newQuantity);
    }

    public void deleteOutOfStock() {
        products.removeIf(p -> p.getQuantity() == 0);
        System.out.println("Da xoa cac san pham het hang");
    }
}

public class minitest {
public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ProductManager manager = new ProductManager();
        while (true) {
            System.out.println("====== PRODUCT MANAGEMENT SYSTEM ======");
            System.out.println("1. Them san pham");
            System.out.println("2. Hien thi danh sach");
            System.out.println("3. Cap nhat so luong");
            System.out.println("4. Xoa san pham het hang");
            System.out.println("5. Thoat");
            System.out.print("Chon: ");

            int choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1:
                    try {
                        System.out.print("Nhap id: ");
                        int id = Integer.parseInt(sc.nextLine());

                        System.out.print("Nhap ten: ");
                        String name = sc.nextLine();

                        System.out.print("Nhap gia: ");
                        double price = Double.parseDouble(sc.nextLine());

                        System.out.print("Nhap so luong: ");
                        int quantity = Integer.parseInt(sc.nextLine());

                        System.out.print("Nhap danh muc: ");
                        String category = sc.nextLine();

                        Product p = new Product(id, name, price, quantity, category);
                        manager.addProduct(p);
                        System.out.println("Them thanh cong");
                    } catch (InvalidProductException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 2:
                    manager.displayProducts();
                    break;
                case 3:
                    try {
                        System.out.print("Nhap id can cap nhat: ");
                        int id = Integer.parseInt(sc.nextLine());

                        System.out.print("Nhap so luong moi: ");
                        int quantity = Integer.parseInt(sc.nextLine());

                        manager.updateQuantity(id, quantity);

                        System.out.println("Cap nhat thanh cong");

                    } catch (InvalidProductException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 4:
                    manager.deleteOutOfStock();
                    break;
                case 5:
                    System.out.println("Thoat chuong trinh");
                    return;
                default:
                    System.out.println("Lua chon khong hop le");
            }
            sc.close();
        }
    }
}