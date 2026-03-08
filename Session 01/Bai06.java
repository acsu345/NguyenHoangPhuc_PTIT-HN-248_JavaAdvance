import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

class InvalidAgeException extends Exception {

    public InvalidAgeException(String msg) {
        super(msg);
    }

}

class User {

    private String name;
    private int age;

    public User(String name) {
        this.name = name;
    }

    public void setAge(int age) throws InvalidAgeException {
        if (age < 0) {
            throw new InvalidAgeException("Tuổi không thể âm!");
        }
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

}

class Logger {

    public static void logError(String message) {

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String time = LocalDateTime.now().format(formatter);

        System.out.println("[ERROR] " + time + " - " + message);
    }

}

public class Bai06 {
    public static void saveToFile() throws IOException {
        throw new IOException("Không thể ghi file!");
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        try {

            System.out.print("Nhập tên người dùng: ");
            String name = sc.nextLine();

            User user = new User(name);

            System.out.print("Nhập năm sinh: ");
            String yearStr = sc.nextLine();

            int year = Integer.parseInt(yearStr);
            int age = 2026 - year;

            user.setAge(age);

            if (user.getName() != null) {
                System.out.println("Tên: " + user.getName());
            }

            System.out.println("Tuổi: " + user.getAge());

          
            System.out.print("Nhập tổng số người: ");
            int total = sc.nextInt();

            System.out.print("Nhập số nhóm: ");
            int groups = sc.nextInt();

            int result = total / groups;

            System.out.println("Mỗi nhóm có: " + result + " người");

            saveToFile();

        }

        catch (NumberFormatException e) {
            Logger.logError("Sai định dạng số: " + e.getMessage());
        }

        catch (ArithmeticException e) {
            Logger.logError("Không thể chia cho 0!");
        }

        catch (InvalidAgeException e) {
            Logger.logError(e.getMessage());
        }

        catch (IOException e) {
            Logger.logError("Lỗi hệ thống khi ghi file: " + e.getMessage());
        }

        finally {
            sc.close();
            System.out.println("Dọn dẹp tài nguyên...");
        }

        System.out.println("Chương trình kết thúc an toàn.");

    }
}
