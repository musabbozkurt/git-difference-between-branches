package java;

import java.services.CommitService;
import java.services.CommitServiceImpl;
import java.services.model.User;
import java.util.List;
import java.util.Optional;

public class MainClass {
    public static void main(String[] args) {
        CommitService commitService = new CommitServiceImpl("Musab", "2019-02-01");
        commitService.run();
/*
        public User(String first, String middle, String last, String email)
        User user = new User("Name", null, "surname", "email");

        Optional<User> man = Optional.ofNullable(user);
        man.ifPresent(user1 -> System.out.println("user1 = " + user1));

        Optional<List<User>> users = Optional.empty();
        users.ifPresent(users1 -> users1.add(user));

        System.out.println("users = " + users);
*/
    }
}