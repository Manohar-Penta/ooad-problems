package logging_framework;

import logging_framework.entities.Sink;
import logging_framework.services.LogProcessor;
import logging_framework.enums.LogLevel;
import logging_framework.exceptions.SinkExists;

import java.time.Instant;
import java.time.temporal.TemporalAccessor;
import java.util.Set;

public class Logger {
    private LogProcessor processor;
    private Set<Sink> sinks;

    private Logger() {
    }

    private static class Holder {
        private static final Logger instance = new Logger();
    }

    static public Logger getInstance() {
        return Holder.instance;
    }

    public void log(LogLevel level, String message) {
        TemporalAccessor now = Instant.now();
        processor.queue(level, now, message);
    }

    public void addSink(Sink sink) throws SinkExists {
        if (sinks.contains(sink)) {
            throw new SinkExists(sink);
        }

        sinks.add(sink);
    }

    public void removeSink(Sink sink) {
        sinks.remove(sink);
    }
}
