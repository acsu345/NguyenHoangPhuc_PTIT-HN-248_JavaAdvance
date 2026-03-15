class TicketPool {

    private String roomName;
    private int tickets;

    public TicketPool(String roomName, int tickets) {
        this.roomName = roomName;
        this.tickets = tickets;
    }

    public String getRoomName() {
        return roomName;
    }

    public String getTicket() {
        if (tickets > 0) {
            String ticket = roomName + "-00" + tickets;
            tickets--;
            return ticket;
        }
        return null;
    }

    public void returnTicket() {
        tickets++;
    }
}
class BookingCounter implements Runnable {

    private String name;
    private TicketPool roomA;
    private TicketPool roomB;
    private boolean lockAFirst;

    public BookingCounter(String name, TicketPool roomA, TicketPool roomB, boolean lockAFirst) {
        this.name = name;
        this.roomA = roomA;
        this.roomB = roomB;
        this.lockAFirst = lockAFirst;
    }

    public void sellCombo() {

        if (lockAFirst) {

            synchronized (roomA) {
                System.out.println(name + ": Đã lấy vé phòng " + roomA.getRoomName());

                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                }

                System.out.println(name + ": Đang chờ vé phòng " + roomB.getRoomName());

                synchronized (roomB) {

                    String ticketA = roomA.getTicket();
                    String ticketB = roomB.getTicket();

                    if (ticketA != null && ticketB != null) {
                        System.out.println(name + " bán combo thành công: "
                                + ticketA + " & " + ticketB);
                    } else {

                        if (ticketA != null) {
                            roomA.returnTicket();
                        }

                        if (ticketB != null) {
                            roomB.returnTicket();
                        }

                        System.out.println(name + ": Bán combo thất bại");
                    }
                }
            }

        } else {

            synchronized (roomB) {
                System.out.println(name + ": Đã lấy vé phòng " + roomB.getRoomName());

                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                }

                System.out.println(name + ": Đang chờ vé phòng " + roomA.getRoomName());

                synchronized (roomA) {

                    String ticketA = roomA.getTicket();
                    String ticketB = roomB.getTicket();

                    if (ticketA != null && ticketB != null) {
                        System.out.println(name + " bán combo thành công: "
                                + ticketA + " & " + ticketB);
                    } else {

                        if (ticketA != null) {
                            roomA.returnTicket();
                        }

                        if (ticketB != null) {
                            roomB.returnTicket();
                        }

                        System.out.println(name + ": Bán combo thất bại");
                    }
                }
            }
        }
    }

    @Override
    public void run() {
        sellCombo();
    }
}
public class Bai01 {

    public static void main(String[] args) {

        TicketPool roomA = new TicketPool("A", 2);
        TicketPool roomB = new TicketPool("B", 2);

        BookingCounter counter1 =
                new BookingCounter("Quầy 1", roomA, roomB, true);

        BookingCounter counter2 =
                new BookingCounter("Quầy 2", roomA, roomB, false);

        Thread t1 = new Thread(counter1);
        Thread t2 = new Thread(counter2);

        t1.start();
        t2.start();
    }
}