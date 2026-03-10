import java.util.List;
import java.util.Optional;

class UserRepository {

    private List<User> users = List.of(
            new User("alice", "alice@gmail.com", "ACTIVE"),
            new User("bob", "bob@yahoo.com", "INACTIVE"),
            new User("charlie", "charlie@gmail.com", "ACTIVE")
    );

    public Optional<User> findUserByUsername(String username) {
        return users.stream()
                .filter(u -> u.username().equalsIgnoreCase(username))
                .findFirst();
    }
}
public class Bai03{
    public static void main(String[] args) {

        UserRepository repo = new UserRepository();

        Optional<User> userOpt = repo.findUserByUsername("alice");

        userOpt.ifPresent(u -> System.out.println("Welcome " + u.username()));

        String result = userOpt
                .map(u -> "Welcome " + u.username())
                .orElse("Guest login");

        System.out.println(result);
    }
}