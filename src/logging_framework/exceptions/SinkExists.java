package logging_framework.exceptions;

import logging_framework.entities.Sink;

public class SinkExists extends Exception {
    public SinkExists(Sink sink) {
        super("Already exists : " + sink.toString());
    }
}
