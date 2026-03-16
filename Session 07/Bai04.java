import java.util.ArrayList;
import java.util.List;

class Order {
    String id;

    public Order(String id) {
        this.id = id;
    }
}

interface OrderRepository {
    void save(Order order);
    List<Order> findAll();
}

interface NotificationService {
    void send(String message, String recipient);
}

class FileOrderRepository implements OrderRepository {

    List<Order> orders = new ArrayList<>();

    public void save(Order order) {
        orders.add(order);
        System.out.println("Lưu đơn hàng vào file: " + order.id);
    }

    public List<Order> findAll() {
        return orders;
    }
}

class DatabaseOrderRepository implements OrderRepository {

    List<Order> orders = new ArrayList<>();

    public void save(Order order) {
        orders.add(order);
        System.out.println("Lưu đơn hàng vào database: " + order.id);
    }

    public List<Order> findAll() {
        return orders;
    }
}

class EmailService implements NotificationService {

    public void send(String message, String recipient) {
        System.out.println("Gửi email: " + message);
    }
}

class SMSNotification implements NotificationService {

    public void send(String message, String recipient) {
        System.out.println("Gửi SMS: " + message);
    }
}

class OrderService {

    private OrderRepository orderRepository;
    private NotificationService notificationService;

    public OrderService(OrderRepository orderRepository, NotificationService notificationService) {
        this.orderRepository = orderRepository;
        this.notificationService = notificationService;
    }

    public void createOrder(Order order) {
        orderRepository.save(order);
        notificationService.send("Đơn hàng " + order.id + " đã được tạo", "customer");
    }
}

public class Bai04 {

    public static void main(String[] args) {

        OrderRepository repo1 = new FileOrderRepository();
        NotificationService notify1 = new EmailService();

        OrderService service1 = new OrderService(repo1, notify1);

        Order order1 = new Order("ORD001");
        service1.createOrder(order1);

        OrderRepository repo2 = new DatabaseOrderRepository();
        NotificationService notify2 = new SMSNotification();

        OrderService service2 = new OrderService(repo2, notify2);

        Order order2 = new Order("ORD002");
        service2.createOrder(order2);
    }
}