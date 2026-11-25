package stackover_flow.interfaces;

import stackover_flow.entities.User;
import stackover_flow.enums.VoteType;

public interface Votable {
    void vote(VoteType type, User voterId);

    int getVotes(VoteType type);
}
