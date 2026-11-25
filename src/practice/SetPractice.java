package practice;

import java.util.Set;
import java.util.TreeSet;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Random;

public class SetPractice {

    public static void main(String[] args) {
        Random rand = new Random();
        Set<User> st = new TreeSet<>((o1, o2) -> o2.age - o1.age);
        // Set<User> st = new LinkedHashSet<>(); // retains order in which keys are
        // inserted
        // Set<User> st = new HashSet<>();
        for (int i = 1; i <= 10; i++) {
            int val = rand.nextInt(100);
            System.out.print(val + " ");
            st.add(new User("Pushpa " + i, val));
        }
        System.out.println();
        for (User user : st) {
            System.out.print(user.age + " ");
        }
    }

    record User(String name, int age) {
    }
}
