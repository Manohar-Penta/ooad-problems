package stackover_flow;

import stackover_flow.entities.Answer;
import stackover_flow.entities.Question;
import stackover_flow.entities.User;
import stackover_flow.enums.ReputationType;
import stackover_flow.enums.VoteType;
import stackover_flow.interfaces.Commentable;
import stackover_flow.interfaces.Votable;
import stackover_flow.service.ReputationService;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StackOverFlow {
    private final Map<String, User> users;
    private final Map<String, Question> questions;
    private final Map<String, Answer> answers;
    private final Map<String, Set<String>> tagIndex;
    private final ReputationService reputationService;

    public StackOverFlow() {
        this.users = new HashMap<>();
        this.questions = new HashMap<>();
        this.answers = new HashMap<>();
        this.tagIndex = new HashMap<>();
        this.reputationService = new ReputationService();
    }

    public User addUser(String name) {
        User newUser = new User(name);
        users.put(newUser.getId(), newUser);
        return newUser;
    }

    public Question postQuestion(String content, String authorId, List<String> tags) {
        if (!users.containsKey(authorId)) {
            System.err.println("Unregistered User!!");
            return null;
        }
        Question ques = new Question(content, users.get(authorId), tags, reputationService);
        questions.put(ques.getId(), ques);
        for (String tag : tags) {
            tagIndex.computeIfAbsent(tag, k -> new HashSet<String>()).add(ques.getId());
        }
        return ques;
    }

    public Answer postAnswer(String content, String authorId, String quesId) {
        if (!questions.containsKey(quesId)) {
            System.err.println("Question not found!!");
            return null;
        }
        if (!users.containsKey(authorId)) {
            System.err.println("User not found!!");
            return null;
        }

        Question ques = questions.get(quesId);
        Answer ans = new Answer(content, users.get(authorId), reputationService);
        ques.addAnswer(ans);
        answers.put(ans.getId(), ans);
        return ans;
    }

    public void vote(VoteType type, Votable votable, String voterId) {
        votable.vote(type, users.get(voterId));
    }

    public void comment(String content, Commentable commentable, String commenterId) {
        commentable.comment(content, users.get(commenterId));
    }

    public List<Question> searchTag(String tag) {
        return tagIndex.getOrDefault(tag, new HashSet<>()).stream().map((quesId) -> questions.get(quesId)).toList();
    }

    public List<Question> searchUser(String name) {
        Set<String> usersFound = new HashSet<>();
        String[] name_parts = name.split(" ");
        for (String part : name_parts) {
            List<String> usersWithCurrPart = this.users.values().stream()
                    .filter((user) -> user.getName().contains(part)).map((user) -> user.getId())
                    .toList();
            usersFound.addAll(usersWithCurrPart);
        }
        List<Question> filteredQuestions = this.questions.values().stream()
                .filter(ques -> usersFound.contains(ques.getAuthor().getId())).toList();
        return filteredQuestions;
    }

    public boolean acceptAnswer(String quesId, String ansId, String accepter) {
        if (!questions.containsKey(quesId)) {
            System.err.println("Question not found!!");
            return false;
        }
        if (!answers.containsKey(ansId)) {
            System.err.println("Answer not found!!");
            return false;
        }
        if (!users.containsKey(accepter)) {
            System.err.println("User not found!!");
            return false;
        }
        Answer foundAns = answers.get(ansId);
        User accepterUser = users.get(accepter);
        boolean accepted = questions.get(quesId).acceptAnswer(foundAns, accepterUser);
        return accepted;
    }

}
