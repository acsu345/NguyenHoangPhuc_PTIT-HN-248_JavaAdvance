import java.util.Scanner;

interface DiscountStrategy{
    double apply(double total);
}

interface PaymentMethod{
    void pay(double amount);
}

interface NotificationService{
    void notifyUser(String message);
}

interface SalesChannelFactory{
    DiscountStrategy createDiscount();
    PaymentMethod createPayment();
    NotificationService createNotification();
}

class WebsiteDiscount implements DiscountStrategy{
    public double apply(double total){
        System.out.println("Áp dụng giảm giá 10% cho đơn hàng website");
        return total*0.9;
    }
}

class WebsitePayment implements PaymentMethod{
    public void pay(double amount){
        System.out.println("Xử lý thanh toán thẻ tín dụng qua cổng thanh toán online");
    }
}

class EmailNotification implements NotificationService{
    public void notifyUser(String message){
        System.out.println("Gửi email xác nhận");
    }
}

class WebsiteFactory implements SalesChannelFactory{

    public DiscountStrategy createDiscount(){
        return new WebsiteDiscount();
    }

    public PaymentMethod createPayment(){
        return new WebsitePayment();
    }

    public NotificationService createNotification(){
        return new EmailNotification();
    }
}

class MobileDiscount implements DiscountStrategy{
    public double apply(double total){
        System.out.println("Áp dụng giảm giá 15% cho lần đầu");
        return total*0.85;
    }
}

class MomoPayment implements PaymentMethod{
    public void pay(double amount){
        System.out.println("Xử lý thanh toán MoMo tích hợp");
    }
}

class PushNotification implements NotificationService{
    public void notifyUser(String message){
        System.out.println("Gửi push notification: Đơn hàng thành công");
    }
}

class MobileFactory implements SalesChannelFactory{

    public DiscountStrategy createDiscount(){
        return new MobileDiscount();
    }

    public PaymentMethod createPayment(){
        return new MomoPayment();
    }

    public NotificationService createNotification(){
        return new PushNotification();
    }
}

class POSDiscount implements DiscountStrategy{
    public double apply(double total){
        System.out.println("Áp dụng giảm giá thành viên tại cửa hàng");
        return total*0.95;
    }
}

class CODPayment implements PaymentMethod{
    public void pay(double amount){
        System.out.println("Thanh toán tiền mặt tại cửa hàng");
    }
}

class PrintNotification implements NotificationService{
    public void notifyUser(String message){
        System.out.println("In hóa đơn giấy tại quầy");
    }
}

class POSFactory implements SalesChannelFactory{

    public DiscountStrategy createDiscount(){
        return new POSDiscount();
    }

    public PaymentMethod createPayment(){
        return new CODPayment();
    }

    public NotificationService createNotification(){
        return new PrintNotification();
    }
}

class OrderService{

    DiscountStrategy discount;
    PaymentMethod payment;
    NotificationService notification;

    OrderService(SalesChannelFactory factory){
        discount=factory.createDiscount();
        payment=factory.createPayment();
        notification=factory.createNotification();
    }

    void createOrder(String product,double price){

        System.out.println("Tạo đơn hàng cho sản phẩm: "+product);

        double finalAmount=discount.apply(price);

        payment.pay(finalAmount);

        notification.notifyUser("done");
    }
}

public class Bai06{

    public static void main(String[] args){

        Scanner sc=new Scanner(System.in);

        System.out.println("1. Website");
        System.out.println("2. Mobile App");
        System.out.println("3. POS");

        int choice=sc.nextInt();

        SalesChannelFactory factory=null;

        if(choice==1){
            System.out.println("Bạn đã chọn kênh Website");
            factory=new WebsiteFactory();
        }
        else if(choice==2){
            System.out.println("Bạn đã chọn kênh Mobile App");
            factory=new MobileFactory();
        }
        else if(choice==3){
            System.out.println("Bạn đã chọn kênh POS");
            factory=new POSFactory();
        }

        OrderService service=new OrderService(factory);

        if(choice==1){
            service.createOrder("Laptop",15000000);
        }
        else if(choice==2){
            service.createOrder("Điện thoại",10000000);
        }
        else{
            service.createOrder("Sản phẩm tại cửa hàng",5000000);
        }
    }
}