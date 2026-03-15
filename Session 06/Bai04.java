


import java.util.Random;
import java.util.ArrayList;
import java.util.List;

class K4Ticket {

    String ticketId;
    String roomName;
    boolean isSold;

    public K4Ticket(String ticketId, String roomName) {
        this.ticketId = ticketId;
        this.roomName = roomName;
        this.isSold = false;
    }
}

class K4TicketPool {

    String roomName;
    List<K4Ticket> tickets = new ArrayList<>();

    public K4TicketPool(String roomName, int totalTickets) {
        this.roomName = roomName;

        for (int i = 1; i <= totalTickets; i++) {
            String id = roomName + "-" + String.format("%03d", i);
            tickets.add(new K4Ticket(id, roomName));
        }
    }

    public synchronized K4Ticket sellTicket() {

        for (K4Ticket t : tickets) {
            if (!t.isSold) {
                t.isSold = true;
                return t;
            }
        }

        return null;
    }

    public int getRemainingTickets() {

        int count = 0;

        for (K4Ticket t : tickets) {
            if (!t.isSold) {
                count++;
            }
        }

        return count;
    }
}



class K4BookingCounter implements Runnable {

    String counterName;
    K4TicketPool roomA;
    K4TicketPool roomB;
    int soldCount = 0;

    Random random = new Random();

    public K4BookingCounter(String counterName, K4TicketPool roomA, K4TicketPool roomB) {
        this.counterName = counterName;
        this.roomA = roomA;
        this.roomB = roomB;
    }

    @Override
    public void run() {

        while (true) {

            if (roomA.getRemainingTickets() == 0 && roomB.getRemainingTickets() == 0) {
                break;
            }

            int choice = random.nextInt(2);

            K4Ticket ticket = null;

            if (choice == 0) {

                ticket = roomA.sellTicket();

                if (ticket == null) {
                    ticket = roomB.sellTicket();
                }

            } else {

                ticket = roomB.sellTicket();

                if (ticket == null) {
                    ticket = roomA.sellTicket();
                }
            }

            if (ticket != null) {
                soldCount++;
                System.out.println(counterName + " đã bán vé " + ticket.ticketId);
            }

            try {
                Thread.sleep(100);
            } catch (Exception e) {
            }
        }

        System.out.println(counterName + " bán được: " + soldCount + " vé");
    }
}
public class Bai04{

    public static void main(String[] args) {

        K4TicketPool roomA = new K4TicketPool("A", 10);
        K4TicketPool roomB = new K4TicketPool("B", 10);

        K4BookingCounter counter1 =
                new K4BookingCounter("Quầy 1", roomA, roomB);

        K4BookingCounter counter2 =
                new K4BookingCounter("Quầy 2", roomA, roomB);

        Thread t1 = new Thread(counter1);
        Thread t2 = new Thread(counter2);

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (Exception e) {
        }

        System.out.println("Vé còn lại phòng A: " + roomA.getRemainingTickets());
        System.out.println("Vé còn lại phòng B: " + roomB.getRemainingTickets());
    }
}