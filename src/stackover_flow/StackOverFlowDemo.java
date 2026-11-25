package stackover_flow;

import java.util.ArrayList;
import java.util.List;

import stackover_flow.entities.Answer;
import stackover_flow.entities.Question;
import stackover_flow.entities.User;
import stackover_flow.enums.VoteType;

public class StackOverFlowDemo {
    public static void main(String[] args) {
        StackOverFlow stof = new StackOverFlow();

        // Create Question, Add answers, accept answer
        User user1 = stof.addUser("Curious Kid");
        List<String> tags = new ArrayList<>();
        tags.add("Java");
        Question Q1 = stof.postQuestion("Why is Java so popular?", user1.getId(), tags);
        User user2 = stof.addUser("Serious Geek");
        User user3 = stof.addUser("Laid back jonny");
        Answer A1Q1 = stof.postAnswer("It is stable & reliable", user2.getId(), Q1.getId());
        Answer A2Q1 = stof.postAnswer("It is fun!1", user3.getId(), Q1.getId());
        boolean u3Status = stof.acceptAnswer(Q1.getId(), A2Q1.getId(), user3.getId());
        System.out.println("User 3 accepting succeded? : " + u3Status);
        boolean u1Status = stof.acceptAnswer(Q1.getId(), A1Q1.getId(), user1.getId());
        System.out.println("User 1 accepting succeded? : " + u1Status);
        System.out.println("User 2 reputation : " + user2.getReputation());
        System.out.println("User 3 reputation : " + user3.getReputation());
        System.out.println("Accepting User 3's Answer...");
        stof.acceptAnswer(Q1.getId(), A2Q1.getId(), user1.getId());
        System.out.println("User 2 reputation : " + user2.getReputation());
        System.out.println("User 3 reputation : " + user3.getReputation());
        stof.vote(VoteType.UP, A2Q1, user3.getId());
        stof.vote(VoteType.UP, A2Q1, user1.getId());
        System.out.println("User 3 reputation : " + user3.getReputation());
    }
}
