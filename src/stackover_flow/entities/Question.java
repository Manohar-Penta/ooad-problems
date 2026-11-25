package stackover_flow.entities;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import stackover_flow.base_classes.Post;
import stackover_flow.enums.ReputationType;
import stackover_flow.enums.VoteType;
import stackover_flow.service.ReputationService;

public class Question extends Post {
    private Set<Answer> answers;
    private Answer acceptedAnswer;
    private Set<String> tags;

    public Question(String content, User author, List<String> quesTags, ReputationService service) {
        super(author, content, service);
        this.answers = new HashSet<>();
        this.tags = new HashSet<>(quesTags);
    }

    public boolean addAnswer(Answer ans) {
        this.answers.add(ans);
        return true;
    }

    public boolean acceptAnswer(Answer ans, User accepter) {
        if (!answers.contains(ans)) {
            System.err.println("Answer not found for accpet answer request");
            return false;
        }
        if (!accepter.equals(getAuthor())) {
            System.err.println("User is not the owner of the question.");
            return false;
        }
        if (acceptedAnswer != null) {
            reputationService.revert(acceptedAnswer.getAuthor(), ReputationType.ACCEPT_ANSWER);
        }
        acceptedAnswer = ans;
        reputationService.apply(acceptedAnswer.getAuthor(), ReputationType.ACCEPT_ANSWER);
        return true;
    }

    public Answer getAcceptedAnswer() {
        return this.acceptedAnswer;
    }

    public boolean isAnswerAccepted() {
        return acceptedAnswer != null;
    }

    public boolean containsTag(String tag) {
        return tags.contains(tag);
    }

    @Override
    protected ReputationType reputationFor(VoteType type) {
        return type.equals(VoteType.UP) ? ReputationType.QUESTION_UPVOTE : ReputationType.QUESTION_DOWNVOTE;
    }
}
