package stackover_flow.interfaces;

import stackover_flow.entities.Comment;
import stackover_flow.entities.User;

import java.util.List;

public interface Commentable {
    Comment comment(String content, User commenter);

    List<Comment> getComments();
}
