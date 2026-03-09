import java.util.*;
import java.util.function.*;

class User {
    private String username;

    public User() {
        this.username = "defaultUser";
    }

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}

public class Bai04 {
    public static void main(String[] args) {

        List<User> users = Arrays.asList(
                new User("alice"),
                new User("bob"),
                new User("charlie")
        );

        Function<User, String> getUsername = User::getUsername;

        Consumer<String> print = System.out::println;

        users.stream()
                .map(getUsername)
                .forEach(print);

        Supplier<User> createUser = User::new;

        User newUser = createUser.get();
        System.out.println("New user: " + newUser.getUsername());
    }
}