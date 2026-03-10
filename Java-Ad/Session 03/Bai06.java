import java.util.List;
import java.util.stream.Collectors;

record Post(String title, List<String> tags) {}

public class Bai06{
    public static void main(String[] args) {

        List<Post> posts = List.of(
                new Post("Java Post", List.of("java", "backend")),
                new Post("Python Post", List.of("python", "data"))
        );

        List<String> allTags = posts.stream()
                .flatMap(post -> post.tags().stream())
                .collect(Collectors.toList());

        System.out.println(allTags);
    }
}