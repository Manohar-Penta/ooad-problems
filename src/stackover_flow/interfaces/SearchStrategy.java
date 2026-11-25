package stackover_flow.interfaces;

import java.util.List;

import stackover_flow.entities.Question;

public interface SearchStrategy {
    List<Question> search();
}
