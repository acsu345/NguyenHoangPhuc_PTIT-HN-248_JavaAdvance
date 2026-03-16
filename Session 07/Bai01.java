import java.util.*;

class Product{
    String ProductId;
    String ProductName;
    double Price ;

    public Product(String ProductId, String ProductName, double Price){
        this.ProductId = ProductId;
        this.ProductName = ProductName;
        this.Price = Price;
    }
}

class Customer{
    String CustomerId;
    String CustomerName;
    String Email;

    public Customer(String CustomerId, String CustomerName, String Email){
        this.CustomerId = CustomerId;
        this.CustomerName = CustomerName;
        this.Email = Email;
    }
}

class OrderItem{
    Product product;
    int quantity;
    public OrderItem(Product product, int quantity){
        this.product = product;
        this.quantity = quantity;
    }
}

class Order{
    String orderId;
    Customer customer;
    List<OrderItem> items = new ArrayList<>();
    double total;

    public Order(String orderId, Customer customer) {
        this.orderId = orderId;
        this.customer = customer;
    }

    public void addItem(Product product, int quantity) {
        items.add(new OrderItem(product, quantity));
    }
}

class OrderCalculator {
    public static double calculateTotal(Order order) {
        double total = 0;
        for (OrderItem item : order.items) {
            total += item.product.Price * item.quantity;
        }
        order.total = total;
        return total;
    }
}
class OrderRespository {
    public void save(Order order){
        System.out.println("Đã lưu đơn hàng với mã: " + order.orderId);
    }
}

class EmailService{
    public void sendEmail(Customer customer, String message){
        System.out.println("Đã gửi email đến " + customer.Email + " với nội dung: " + message);
    }}
public class Bai01 {
    public static void main(String[] args){
        Product product1 = new Product("P001", "Sản phẩm 1", 100.0);
        Product product2 = new Product("P002", "Sản phảm 2", 200.0);

        Customer customer = new Customer("C001", "Khách 01", "K01@gmail.com");

        Order order = new Order("O001", customer);
        order.addItem(product2, 2);
        order.addItem(product1, 2);

        double total = OrderCalculator.calculateTotal(order);

        OrderRespository orderRespository = new OrderRespository();
        orderRespository.save(order);
        EmailService emailService = new EmailService();
        emailService.sendEmail(customer, "Đơn hàng của bạn đã được lưu với tổng giá trị: " + total);
    }
}
