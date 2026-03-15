class TicketPoolWaitNotify {

    private String roomName;
    private int tickets;
    private int ticketNumber = 1;

    public TicketPoolWaitNotify(String roomName, int tickets) {
        this.roomName = roomName;
        this.tickets = tickets;
    }

    public synchronized void sellTicket(String counterName) {

        while (tickets == 0) {
            try {
                System.out.println(counterName + ": Hết vé phòng " + roomName + ", đang chờ...");
                wait();
            } catch (InterruptedException e) {
            }
        }

        String ticket = roomName + "-" + String.format("%03d", ticketNumber);
        ticketNumber++;
        tickets--;

        System.out.println(counterName + " bán vé " + ticket);
    }

    public synchronized void addTickets(int amount) {

        tickets += amount;

        System.out.println("Nhà cung cấp: Đã thêm " + amount + " vé vào phòng " + roomName);

        notifyAll();
    }
}
class BookingCounterWaitNotify implements Runnable {

    private String name;
    private TicketPoolWaitNotify pool;

    public BookingCounterWaitNotify(String name, TicketPoolWaitNotify pool) {
        this.name = name;
        this.pool = pool;
    }

    @Override
    public void run() {

        while (true) {

            pool.sellTicket(name);

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }
        }
    }
}
class TicketSupplier implements Runnable {

    private TicketPoolWaitNotify pool;

    public TicketSupplier(TicketPoolWaitNotify pool) {
        this.pool = pool;
    }

    @Override
    public void run() {

        try {

            Thread.sleep(4000);

            pool.addTickets(3);

        } catch (InterruptedException e) {
        }
    }
}
public class Bai03 {

    public static void main(String[] args) {

        TicketPoolWaitNotify roomA = new TicketPoolWaitNotify("A", 2);
        TicketPoolWaitNotify roomB = new TicketPoolWaitNotify("B", 5);

        BookingCounterWaitNotify counter1 =
                new BookingCounterWaitNotify("Quầy 1", roomA);

        BookingCounterWaitNotify counter2 =
                new BookingCounterWaitNotify("Quầy 2", roomB);

        TicketSupplier supplier = new TicketSupplier(roomA);

        Thread t1 = new Thread(counter1);
        Thread t2 = new Thread(counter2);
        Thread t3 = new Thread(supplier);

        t1.start();
        t2.start();
        t3.start();
    }
}