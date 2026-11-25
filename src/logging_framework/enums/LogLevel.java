package logging_framework.enums;

public enum LogLevel {

    INFO(1), DEBUG(2), WARN(3), ERROR(5), CRITICAL(10);

    private int value;

    private LogLevel(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
