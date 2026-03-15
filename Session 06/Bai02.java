class Bai02TicketPool {

    private String roomName;
    private int tickets;
    private int ticketNumber = 1;

    public Bai02TicketPool(String roomName, int tickets) {
        this.roomName = roomName;
        this.tickets = tickets;
    }

    public synchronized String sellTicket(String counterName) {

        while (tickets == 0) {
            try {
                System.out.println(counterName + ": No tickets left for room " + roomName + ", waiting...");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        String ticket = roomName + "-0" + ticketNumber;
        ticketNumber++;
        tickets--;

        System.out.println(counterName + " sold ticket " + ticket);

        return ticket;
    }

    public synchronized void addTickets(int amount) {

        tickets += amount;

        System.out.println("Supplier: Added " + amount + " tickets to room " + roomName);

        notifyAll();
    }
}
class Bai02BookingCounter implements Runnable {

    private String name;
    private Bai02TicketPool pool;

    public Bai02BookingCounter(String name, Bai02TicketPool pool) {
        this.name = name;
        this.pool = pool;
    }

    @Override
    public void run() {

        while (true) {

            pool.sellTicket(name);

            try {
                Thread.sleep(500);
            } catch (Exception e) {
            }
        }
    }
}
class Bai02Supplier implements Runnable {

    private Bai02TicketPool pool;

    public Bai02Supplier(Bai02TicketPool pool) {
        this.pool = pool;
    }

    @Override
    public void run() {

        try {

            Thread.sleep(5000);

            pool.addTickets(3);

        } catch (Exception e) {
        }
    }
}
public class Bai02 {

    public static void main(String[] args) {

        Bai02TicketPool roomA = new Bai02TicketPool("A", 2);
        Bai02TicketPool roomB = new Bai02TicketPool("B", 5);

        Bai02BookingCounter counter1 =
                new Bai02BookingCounter("Counter 1", roomA);

        Bai02BookingCounter counter2 =
                new Bai02BookingCounter("Counter 2", roomB);

        Bai02Supplier supplier =
                new Bai02Supplier(roomA);

        Thread t1 = new Thread(counter1);
        Thread t2 = new Thread(counter2);
        Thread t3 = new Thread(supplier);

        t1.start();
        t2.start();
        t3.start();
    }
}