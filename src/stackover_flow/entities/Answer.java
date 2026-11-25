package stackover_flow.entities;

import java.util.UUID;

import stackover_flow.base_classes.Post;
import stackover_flow.enums.ReputationType;
import stackover_flow.enums.VoteType;
import stackover_flow.service.ReputationService;

public class Answer extends Post {

    public Answer(String content, User author, ReputationService service) {
        super(author, content, service);
    }

    @Override
    protected ReputationType reputationFor(VoteType type) {
        return type.equals(VoteType.UP) ? ReputationType.ANSWER_UPVOTE : ReputationType.ANSWER_DOWNVOTE;
    }
}
