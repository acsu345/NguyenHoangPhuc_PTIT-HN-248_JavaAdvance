 class InvalidAgeException extends Exception {

    public InvalidAgeException(String msg) {
        super(msg); 
    }
}
class User {

    private int age;

    public void setAge(int age) throws InvalidAgeException {
        if (age < 0) {
            throw new InvalidAgeException("Tuổi không thể âm!");
        }
        this.age = age;
    }

    public int getAge() {
        return age;
    }
}
public class Bai05 {

    public static void main(String[] args) {

        User user = new User();

        try {
            user.setAge(-3); 
            System.out.println("Tuổi: " + user.getAge());
        } 
        catch (InvalidAgeException e) {
            System.out.println("Lỗi: " + e.getMessage());
            e.printStackTrace(); 
        }

        System.out.println("Chương trình vẫn tiếp tục chạy...");
    }
}