package Session03.baitap02;

import java.util.Arrays;
import java.util.List;

public class Main {
    public record User (String userName, String email, String status){}
    public static void main(String[] args) {
        List<User> user = Arrays.asList( new User("alice", "alice@gmail.com","Active"),
                new User("bob", "bob@gmail.com","Inactive"),
                new User("charlie", "charlie@gmail.com","Active"));
        user.stream().filter(email->email.email.endsWith("@gmail.com"))
                .map(email->email.email.split("@")[0])
                .forEach(System.out::println);

    }
}
