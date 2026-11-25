package stackover_flow.service;

import stackover_flow.entities.User;
import stackover_flow.enums.ReputationType;

public class ReputationService {
    public int apply(User user, ReputationType type) {
        return user.updateReputation(type.getValue());
    }

    public int revert(User user, ReputationType type) {
        return user.updateReputation(-1 * type.getValue());
    }
}
