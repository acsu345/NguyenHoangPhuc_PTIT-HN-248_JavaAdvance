import java.util.Random;

public class test01 {
    
    public static void main(String[] args) {
        RandomNameThread thread = new RandomNameThread();
        thread.start();
    }
}

class RandomNameThread extends Thread {
    private String[] names = {
        "An", "Binh", "Cuong", "Dung", "Hoa", "Khanh", "Lan", "Minh", "Nga", "Phuong"
    };
    private Random random = new Random();

    @Override
    public void run() {
        while (true) {
            int idx = random.nextInt(names.length);
            System.out.println("Tên bạn ngẫu nhiên: " + names[idx]);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}