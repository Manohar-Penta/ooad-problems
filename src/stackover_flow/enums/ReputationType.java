package stackover_flow.enums;

public enum ReputationType {
    QUESTION_UPVOTE(1), QUESTION_DOWNVOTE(-1), ANSWER_UPVOTE(3), ANSWER_DOWNVOTE(-1), ACCEPT_ANSWER(5);

    private final int value;

    private ReputationType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
