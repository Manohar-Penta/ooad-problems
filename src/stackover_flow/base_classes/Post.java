package stackover_flow.base_classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import stackover_flow.entities.Comment;
import stackover_flow.entities.User;
import stackover_flow.enums.ReputationType;
import stackover_flow.enums.VoteType;
import stackover_flow.interfaces.Commentable;
import stackover_flow.interfaces.Votable;
import stackover_flow.service.ReputationService;

public abstract class Post implements Commentable, Votable {
    private final User author;
    private String content;
    private final String id;
    private final List<Comment> comments;
    private final Map<String, VoteType> votes;
    protected final ReputationService reputationService;

    protected Post(User author, String content, ReputationService reputationService) {
        this.author = author;
        this.content = content;
        this.id = UUID.randomUUID().toString();
        this.comments = new ArrayList<>();
        this.votes = new HashMap<>();
        this.reputationService = reputationService;
    }

    public String getId() {
        return id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public User getAuthor() {
        return author;
    }

    protected abstract ReputationType reputationFor(VoteType type);

    @Override
    public void vote(VoteType type, User voter) {
        if (voter.getId().equals(author.getId())) {
            System.err.println("User cannot vote on his own Questions or Answers.");
            return;
        }
        VoteType previous = votes.put(voter.getId(), type);
        if (previous == type)
            return;
        if (previous != null)
            reputationService.revert(author, reputationFor(previous));
        reputationService.apply(author, reputationFor(type));
    }

    @Override
    public int getVotes(VoteType type) {
        return (int) votes.values().stream().filter(vote -> vote.equals(type)).count();
    }

    @Override
    public Comment comment(String content, User commenter) {
        Comment newComment = new Comment(content, commenter);
        comments.add(newComment);
        return newComment;
    }

    @Override
    public List<Comment> getComments() {
        return List.copyOf(comments);
    }
}
