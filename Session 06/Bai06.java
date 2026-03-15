import java.util.*;
import java.util.concurrent.*;
import java.lang.management.*;

class K6Ticket {

    String ticketId;
    String roomName;
    boolean isSold = false;

    public K6Ticket(String ticketId, String roomName) {
        this.ticketId = ticketId;
        this.roomName = roomName;
    }
}

class K6Room {

    String roomName;
    List<K6Ticket> tickets = new ArrayList<>();

    public K6Room(String roomName, int totalTickets) {

        this.roomName = roomName;

        for (int i = 1; i <= totalTickets; i++) {

            String id = roomName + "-" + String.format("%03d", i);
            tickets.add(new K6Ticket(id, roomName));
        }
    }

    public synchronized K6Ticket sellTicket() {

        for (K6Ticket t : tickets) {

            if (!t.isSold) {

                t.isSold = true;
                return t;
            }
        }

        return null;
    }

    public int soldCount() {

        int count = 0;

        for (K6Ticket t : tickets) {
            if (t.isSold) count++;
        }

        return count;
    }

    public int totalTickets() {
        return tickets.size();
    }

    public synchronized void addTickets(int amount) {

        int start = tickets.size() + 1;

        for (int i = 0; i < amount; i++) {

            String id = roomName + "-" + String.format("%03d", start + i);
            tickets.add(new K6Ticket(id, roomName));
        }

        System.out.println("Đã thêm " + amount + " vé vào phòng " + roomName);
    }
}

class K6BookingCounter implements Runnable {

    String name;
    List<K6Room> rooms;
    boolean running = true;

    public K6BookingCounter(String name, List<K6Room> rooms) {
        this.name = name;
        this.rooms = rooms;
    }

    @Override
    public void run() {

        Random random = new Random();

        while (running) {

            int index = random.nextInt(rooms.size());
            K6Room room = rooms.get(index);

            K6Ticket ticket = room.sellTicket();

            if (ticket != null) {

                System.out.println(name + " bán vé " + ticket.ticketId);

            }

            try {
                Thread.sleep(500);
            } catch (Exception e) {
            }
        }
    }

    public void stop() {
        running = false;
    }
}


class K6DeadlockDetector implements Runnable {

    @Override
    public void run() {

        ThreadMXBean bean = ManagementFactory.getThreadMXBean();

        long[] threadIds = bean.findDeadlockedThreads();

        System.out.println("Đang quét deadlock...");

        if (threadIds == null) {

            System.out.println("Không phát hiện deadlock.");

        } else {

            System.out.println("Phát hiện DEADLOCK!");
        }
    }
}


class K6Statistics {
    public static void show(List<K6Room> rooms) {
        System.out.println("===== THỐNG KÊ PHÒNG =====");
        for (K6Room room : rooms) {
            System.out.println("Phòng " + room.roomName + ": "
                    + room.soldCount() + "/" + room.totalTickets() + " vé đã bán");
        }
    }
}

public class Bai06 {

    static Scanner sc = new Scanner(System.in);

    static List<K6Room> rooms = new ArrayList<>();
    static List<K6BookingCounter> counters = new ArrayList<>();

    static ExecutorService executor;

    public static void main(String[] args) {

        while (true) {

            System.out.println("\n===== CINEMA SYSTEM =====");
            System.out.println("1. Bắt đầu mô phỏng");
            System.out.println("2. Tạm dừng mô phỏng");
            System.out.println("3. Tiếp tục mô phỏng");
            System.out.println("4. Thêm vé vào phòng");
            System.out.println("5. Xem thống kê");
            System.out.println("6. Phát hiện deadlock");
            System.out.println("7. Thoát");

            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    startSimulation();
                    break;

                case 2:
                    pauseSimulation();
                    break;

                case 3:
                    resumeSimulation();
                    break;

                case 4:
                    addTickets();
                    break;

                case 5:
                    K6Statistics.show(rooms);
                    break;

                case 6:
                    new Thread(new K6DeadlockDetector()).start();
                    break;

                case 7:
                    stopSystem();
                    return;
            }
        }
    }

    static void startSimulation() {

        System.out.print("Số phòng: ");
        int roomCount = sc.nextInt();

        System.out.print("Vé mỗi phòng: ");
        int ticketPerRoom = sc.nextInt();

        System.out.print("Số quầy: ");
        int counterCount = sc.nextInt();

        rooms.clear();
        counters.clear();

        for (int i = 0; i < roomCount; i++) {

            char name = (char) ('A' + i);
            rooms.add(new K6Room(String.valueOf(name), ticketPerRoom));
        }

        executor = Executors.newFixedThreadPool(counterCount);

        for (int i = 1; i <= counterCount; i++) {

            K6BookingCounter counter =
                    new K6BookingCounter("Quầy " + i, rooms);

            counters.add(counter);

            executor.submit(counter);
        }

        System.out.println(
                "Đã khởi tạo hệ thống với " +
                        roomCount + " phòng, " +
                        ticketPerRoom * roomCount +
                        " vé, " +
                        counterCount + " quầy"
        );
    }

    static void pauseSimulation() {

        for (K6BookingCounter c : counters) {
            c.stop();
        }

        System.out.println("Đã tạm dừng tất cả quầy bán vé.");
    }

    static void resumeSimulation() {

        for (K6BookingCounter c : counters) {
            executor.submit(c);
        }

        System.out.println("Đã tiếp tục hoạt động.");
    }

    static void addTickets() {

        System.out.print("Chọn phòng: ");
        String name = sc.next();

        for (K6Room r : rooms) {

            if (r.roomName.equals(name)) {

                System.out.print("Số vé thêm: ");
                int amount = sc.nextInt();

                r.addTickets(amount);
            }
        }
    }

    static void stopSystem() {

        if (executor != null) {
            executor.shutdownNow();
        }

        System.out.println("Đang dừng hệ thống...");
        System.out.println("Kết thúc chương trình.");
    }
}