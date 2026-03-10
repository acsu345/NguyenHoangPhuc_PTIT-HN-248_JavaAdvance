import java.util.List;
import java.util.Comparator;

record User(String username, String email, String status) {}

public class Bai05{
    public static void main(String[] args) {

        List<User> users = List.of(
                new User("alexander", "alex@gmail.com", "ACTIVE"),
                new User("bob", "bob@gmail.com", "INACTIVE"),
                new User("charlotte", "charlotte@gmail.com", "ACTIVE"),
                new User("Benjamin", "ben@gmail.com", "ACTIVE"),
                new User("anna", "anna@gmail.com", "ACTIVE")
        );

        users.stream()
                .sorted(Comparator.comparingInt((User u) -> u.username().length()).reversed())
                .limit(3)
                .map(User::username)
                .forEach(System.out::println);
    }
}