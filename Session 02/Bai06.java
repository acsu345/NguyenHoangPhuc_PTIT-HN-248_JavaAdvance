@FunctionalInterface
interface UserProcessor {
    String process(User u);
}
class User {
    private String username;

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
class UserUtils {
    public static String convertToUpperCase(User u) {
        return u.getUsername().toUpperCase();
    }
}
public class Bai06 {
    public static void main(String[] args) {

        UserProcessor processor = UserUtils::convertToUpperCase;

        User user = new User("phuc");

        String result = processor.process(user);

        System.out.println(result);
    }
}