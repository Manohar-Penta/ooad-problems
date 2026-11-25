package practice;

import java.util.PriorityQueue;

public class PriorityQueuePractice {
    public static void main(String[] args) {
        PriorityQueue<User> pq = new PriorityQueue<>(10, (a, b) -> b.age - a.age);
        for (int i = 0; i < 10; i++) {
            pq.add(new User("Pushpa " + i, i + 1));
        }

        while (!pq.isEmpty()) {
            System.out.println(pq.poll().age);
        }
    }

    public record User(String name, int age) {
    }
}
