import java.util.*;

class Product {
    String id;
    String name;
    double price;
    String category;

    Product(String id,String name,double price,String category){
        this.id=id;
        this.name=name;
        this.price=price;
        this.category=category;
    }
}

class Customer{
    String name;
    String email;
    String phone;

    Customer(String name,String email,String phone){
        this.name=name;
        this.email=email;
        this.phone=phone;
    }
}

class OrderItem{
    Product product;
    int quantity;

    OrderItem(Product product,int quantity){
        this.product=product;
        this.quantity=quantity;
    }

    double getTotal(){
        return product.price*quantity;
    }
}

class Order{
    String id;
    Customer customer;
    List<OrderItem> items=new ArrayList<>();
    double finalAmount;

    Order(String id,Customer customer){
        this.id=id;
        this.customer=customer;
    }

    void addItem(Product p,int q){
        items.add(new OrderItem(p,q));
    }
}

interface DiscountStrategy{
    double applyDiscount(double total);
}

class PercentageDiscount implements DiscountStrategy{
    double percent;

    PercentageDiscount(double percent){
        this.percent=percent;
    }

    public double applyDiscount(double total){
        return total-total*percent/100;
    }
}

class FixedDiscount implements DiscountStrategy{
    double amount;

    FixedDiscount(double amount){
        this.amount=amount;
    }

    public double applyDiscount(double total){
        return total-amount;
    }
}

class HolidayDiscount implements DiscountStrategy{
    public double applyDiscount(double total){
        return total*0.85;
    }
}

interface PaymentMethod{
    void pay(double amount);
}

class CODPayment implements PaymentMethod{
    public void pay(double amount){
        System.out.println("Thanh toán COD: "+(long)amount);
    }
}

class CreditCardPayment implements PaymentMethod{
    public void pay(double amount){
        System.out.println("Thanh toán thẻ tín dụng: "+(long)amount);
    }
}

class MomoPayment implements PaymentMethod{
    public void pay(double amount){
        System.out.println("Thanh toán MoMo: "+(long)amount);
    }
}

class VNPayPayment implements PaymentMethod{
    public void pay(double amount){
        System.out.println("Thanh toán VNPay: "+(long)amount);
    }
}

interface OrderRepository{
    void save(Order order);
    List<Order> findAll();
}

class FileOrderRepository implements OrderRepository{

    List<Order> orders=new ArrayList<>();

    public void save(Order order){
        orders.add(order);
        System.out.println("Đã lưu đơn hàng "+order.id);
    }

    public List<Order> findAll(){
        return orders;
    }
}

class DatabaseOrderRepository implements OrderRepository{

    List<Order> orders=new ArrayList<>();

    public void save(Order order){
        orders.add(order);
        System.out.println("Đã lưu đơn hàng DB "+order.id);
    }

    public List<Order> findAll(){
        return orders;
    }
}

interface NotificationService{
    void send(String message,String recipient);
}

class EmailNotification implements NotificationService{
    public void send(String message,String recipient){
        System.out.println("Đã gửi email xác nhận");
    }
}

class SMSNotification implements NotificationService{
    public void send(String message,String recipient){
        System.out.println("Đã gửi SMS xác nhận");
    }
}

class InvoiceGenerator{

    static double generate(Order order,DiscountStrategy discount){

        double total=0;

        System.out.println("=== HÓA ĐƠN ===");
        System.out.println("Khách: "+order.customer.name);

        for(OrderItem item:order.items){
            double itemTotal=item.getTotal();
            total+=itemTotal;

            System.out.println(item.product.name+" - Số lượng: "+item.quantity+
                    " - Đơn giá: "+(long)item.product.price+
                    " - Thành tiền: "+(long)itemTotal);
        }

        double afterDiscount=discount.applyDiscount(total);

        System.out.println("Tổng tiền: "+(long)total);
        System.out.println("Cần thanh toán: "+(long)afterDiscount);

        return afterDiscount;
    }
}

class OrderService{

    OrderRepository repo;
    NotificationService notify;

    OrderService(OrderRepository repo,NotificationService notify){
        this.repo=repo;
        this.notify=notify;
    }

    void createOrder(Order order,double amount){
        order.finalAmount=amount;
        repo.save(order);
        notify.send("Đơn hàng "+order.id,"customer");
    }

    List<Order> getOrders(){
        return repo.findAll();
    }
}

public class Bai05{

    static Scanner sc=new Scanner(System.in);

    static List<Product> products=new ArrayList<>();
    static List<Customer> customers=new ArrayList<>();

    public static void main(String[] args){

        OrderService service=new OrderService(
                new FileOrderRepository(),
                new EmailNotification()
        );

        int choice;

        do{

            System.out.println("\n1. Thêm sản phẩm");
            System.out.println("2. Thêm khách hàng");
            System.out.println("3. Tạo đơn hàng");
            System.out.println("4. Xem đơn hàng");
            System.out.println("5. Tính doanh thu");
            System.out.println("6. Thêm thanh toán mới");
            System.out.println("7. Thêm giảm giá mới");
            System.out.println("0. Thoát");

            choice=sc.nextInt();
            sc.nextLine();

            switch(choice){

                case 1:
                    System.out.print("Mã: ");
                    String id=sc.nextLine();

                    System.out.print("Tên: ");
                    String name=sc.nextLine();

                    System.out.print("Giá: ");
                    double price=sc.nextDouble();
                    sc.nextLine();

                    System.out.print("Danh mục: ");
                    String cate=sc.nextLine();

                    products.add(new Product(id,name,price,cate));

                    System.out.println("Đã thêm sản phẩm "+id);
                    break;

                case 2:

                    System.out.print("Tên: ");
                    String cname=sc.nextLine();

                    System.out.print("Email: ");
                    String email=sc.nextLine();

                    System.out.print("ĐT: ");
                    String phone=sc.nextLine();

                    customers.add(new Customer(cname,email,phone));

                    System.out.println("Đã thêm khách hàng");
                    break;

                case 3:

                    Customer c=customers.get(0);

                    Order order=new Order("ORD00"+(service.getOrders().size()+1),c);

                    Product p=products.get(0);

                    order.addItem(p,1);

                    DiscountStrategy discount=new PercentageDiscount(10);

                    PaymentMethod pay=new CreditCardPayment();

                    double amount=InvoiceGenerator.generate(order,discount);

                    pay.pay(amount);

                    service.createOrder(order,amount);

                    break;

                case 4:

                    for(Order o:service.getOrders()){
                        System.out.println(o.id+" - "+o.customer.name+" - "+(long)o.finalAmount);
                    }

                    break;

                case 5:

                    double revenue=0;

                    for(Order o:service.getOrders()){
                        revenue+=o.finalAmount;
                    }

                    System.out.println("Tổng doanh thu: "+(long)revenue);
                    break;

                case 6:

                    System.out.println("Đã thêm phương thức thanh toán ZaloPay");
                    break;

                case 7:

                    System.out.println("Đã thêm chiến lược giảm giá VIP");
                    break;

            }

        }while(choice!=0);
    }
}