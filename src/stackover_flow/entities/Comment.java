package stackover_flow.entities;

import java.util.Map;
import java.util.Set;

import stackover_flow.interfaces.Votable;
import stackover_flow.enums.VoteType;
import stackover_flow.interfaces.Commentable;

// - Comment
//   - parent: Commentable
//   - content: String
//   - user: User
//   - votes: Vote[]
//   - createdAt: DateTime
//   - updatedAt: DateTime
//   * addVote(VoteType type, User voter)
//   * getVotes(VoteType type)

public class Comment {
    private String content;
    private User author;

    public Comment(String content, User author) {
        this.author = author;
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
