import java.util.*;


class K5Ticket {

    String ticketId;
    String roomName;

    boolean isSold = false;
    boolean isHeld = false;
    boolean isVIP = false;

    long holdExpiryTime = 0;
    String heldBy = null;

    public K5Ticket(String ticketId, String roomName) {
        this.ticketId = ticketId;
        this.roomName = roomName;
    }
}


class K5TicketPool {

    String roomName;
    List<K5Ticket> tickets = new ArrayList<>();

    public K5TicketPool(String roomName, int capacity) {
        this.roomName = roomName;

        for (int i = 1; i <= capacity; i++) {
            String id = roomName + "-" + String.format("%03d", i);
            tickets.add(new K5Ticket(id, roomName));
        }
    }

    public synchronized K5Ticket holdTicket(String counterName, boolean vip) {

        for (K5Ticket t : tickets) {

            if (!t.isSold && !t.isHeld) {

                t.isHeld = true;
                t.isVIP = vip;
                t.heldBy = counterName;
                t.holdExpiryTime = System.currentTimeMillis() + 5000;

                System.out.println(counterName + ": Đã giữ vé " + t.ticketId +
                        (vip ? " (VIP)" : "") + ". Thanh toán trong 5s");

                return t;
            }

            if (t.isHeld && !t.isSold) {
                System.out.println(counterName + ": Vé " + t.ticketId +
                        " đang được giữ bởi quầy khác, chờ...");
            }
        }

        return null;
    }

    public synchronized void sellHeldTicket(K5Ticket ticket, String counterName) {

        if (ticket != null && ticket.isHeld && !ticket.isSold) {

            ticket.isSold = true;
            ticket.isHeld = false;

            System.out.println(counterName + ": Thanh toán thành công vé " + ticket.ticketId);
        }
    }

    public synchronized void releaseExpiredTickets() {

        long now = System.currentTimeMillis();

        for (K5Ticket t : tickets) {

            if (t.isHeld && !t.isSold && now > t.holdExpiryTime) {

                t.isHeld = false;
                t.heldBy = null;

                System.out.println("TimeoutManager: Vé " + t.ticketId +
                        " hết hạn giữ, đã trả lại kho");
            }
        }
    }
}



class K5BookingCounter implements Runnable {

    String counterName;
    List<K5TicketPool> pools;

    Random random = new Random();

    public K5BookingCounter(String counterName, List<K5TicketPool> pools) {
        this.counterName = counterName;
        this.pools = pools;
    }

    @Override
    public void run() {

        while (true) {

            int roomIndex = random.nextInt(pools.size());
            boolean vip = random.nextBoolean();

            K5TicketPool pool = pools.get(roomIndex);

            K5Ticket ticket = pool.holdTicket(counterName, vip);

            if (ticket != null) {

                try {
                    Thread.sleep(3000);
                } catch (Exception e) {
                }

                if (random.nextBoolean()) {
                    pool.sellHeldTicket(ticket, counterName);
                }
            }

            try {
                Thread.sleep(1000);
            } catch (Exception e) {
            }
        }
    }
}


class K5TimeoutManager implements Runnable {

    List<K5TicketPool> pools;

    public K5TimeoutManager(List<K5TicketPool> pools) {
        this.pools = pools;
    }

    @Override
    public void run() {

        while (true) {

            for (K5TicketPool pool : pools) {
                pool.releaseExpiredTickets();
            }

            try {
                Thread.sleep(1000);
            } catch (Exception e) {
            }
        }
    }
}
public class Bai05{

     public static void main(String[] args) {

        K5TicketPool roomA = new K5TicketPool("A", 5);
        K5TicketPool roomB = new K5TicketPool("B", 7);
        K5TicketPool roomC = new K5TicketPool("C", 6);

        List<K5TicketPool> pools = Arrays.asList(roomA, roomB, roomC);

        Thread c1 = new Thread(new K5BookingCounter("Quầy 1", pools));
        Thread c2 = new Thread(new K5BookingCounter("Quầy 2", pools));
        Thread c3 = new Thread(new K5BookingCounter("Quầy 3", pools));
        Thread c4 = new Thread(new K5BookingCounter("Quầy 4", pools));
        Thread c5 = new Thread(new K5BookingCounter("Quầy 5", pools));

        Thread timeout = new Thread(new K5TimeoutManager(pools));

        c1.start();
        c2.start();
        c3.start();
        c4.start();
        c5.start();
        timeout.start();
    }
}