interface DiscountStrategy {
    double applyDiscount(double totalAmount);
}

class PercentageDiscount implements DiscountStrategy {
    private double percent;

    public PercentageDiscount(double percent) {
        this.percent = percent;
    }

    public double applyDiscount(double totalAmount) {
        return totalAmount - (totalAmount * percent / 100);
    }
}

class FixedDiscount implements DiscountStrategy {
    private double amount;

    public FixedDiscount(double amount) {
        this.amount = amount;
    }

    public double applyDiscount(double totalAmount) {
        return totalAmount - amount;
    }
}

class NoDiscount implements DiscountStrategy {
    public double applyDiscount(double totalAmount) {
        return totalAmount;
    }
}

class HolidayDiscount implements DiscountStrategy {
    public double applyDiscount(double totalAmount) {
        return totalAmount * 0.85;
    }
}

class OrderCalculator {
    private DiscountStrategy discountStrategy;

    public OrderCalculator(DiscountStrategy discountStrategy) {
        this.discountStrategy = discountStrategy;
    }

    public double calculate(double totalAmount) {
        return discountStrategy.applyDiscount(totalAmount);
    }
}

public class Bai02{
    public static void main(String[] args) {

        double total = 1000000;

        OrderCalculator calc1 = new OrderCalculator(new PercentageDiscount(10));
        System.out.println("Số tiền sau giảm: " + (long)calc1.calculate(total));

        OrderCalculator calc2 = new OrderCalculator(new FixedDiscount(50000));
        System.out.println("Số tiền sau giảm: " + (long)calc2.calculate(total));

        OrderCalculator calc3 = new OrderCalculator(new NoDiscount());
        System.out.println("Số tiền sau giảm: " + (long)calc3.calculate(total));

        OrderCalculator calc4 = new OrderCalculator(new HolidayDiscount());
        System.out.println("Số tiền sau giảm: " + (long)calc4.calculate(total));
    }
}