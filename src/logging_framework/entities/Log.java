package logging_framework.entities;

import java.time.temporal.TemporalAccessor;

public class Log {
    private Sink sink;
    private TemporalAccessor time;
    private String message;
    private String threadName;

    public Log(Sink sink, TemporalAccessor time, String message) {

    }
}
