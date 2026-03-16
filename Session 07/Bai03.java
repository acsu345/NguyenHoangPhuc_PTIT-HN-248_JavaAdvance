interface PaymentMethod {
}

interface CODPayable extends PaymentMethod {
    void processCOD(double amount);
}

interface CardPayable extends PaymentMethod {
    void processCreditCard(double amount);
}

interface EWalletPayable extends PaymentMethod {
    void processMomo(double amount);
}

class CODPayment implements CODPayable {
    public void processCOD(double amount) {
        System.out.println("Xử lý thanh toán COD: " + (long)amount + " - Thành công");
    }
}

class CreditCardPayment implements CardPayable {
    public void processCreditCard(double amount) {
        System.out.println("Xử lý thanh toán thẻ tín dụng: " + (long)amount + " - Thành công");
    }
}

class MomoPayment implements EWalletPayable, CardPayable {
    public void processMomo(double amount) {
        System.out.println("Xử lý thanh toán MoMo: " + (long)amount + " - Thành công");
    }

    public void processCreditCard(double amount) {
        System.out.println("Xử lý thanh toán MoMo: " + (long)amount + " - Thành công");
    }
}

class PaymentProcessor {

    public void processCOD(CODPayable payment, double amount) {
        payment.processCOD(amount);
    }

    public void processCard(CardPayable payment, double amount) {
        payment.processCreditCard(amount);
    }

    public void processMomo(EWalletPayable payment, double amount) {
        payment.processMomo(amount);
    }
}

public class Bai03 {
    public static void main(String[] args) {

        PaymentProcessor processor = new PaymentProcessor();

        CODPayment cod = new CODPayment();
        processor.processCOD(cod, 500000);

        CreditCardPayment card = new CreditCardPayment();
        processor.processCard(card, 1000000);

        MomoPayment momo = new MomoPayment();
        processor.processMomo(momo, 750000);

        CardPayable test = new MomoPayment();
        processor.processCard(test, 1000000);
    }
}