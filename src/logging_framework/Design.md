# Logging Framework

## Requirements

- Logging Framework should support multiple log levels like INFO, DEBUG, WARN, ERROR and CRITICAL.
- Logging Framework should support multiple output destinations such as console, file.
- Logging framework should support pluggable formats with time, message and log level. Different sinks can use different formats. the format must be extensible
- Framework should provide a configuration mechanism to set the log level and output destination. Every sink would have it's own configuration.
- The logging framework should be thread-safe to handle concurrent logging from multiple threads.
- It should be extensible to accommodate new log levels and output destinations in the future.

## Entities & Relationships

- Logging Framework - Logger // orchestrator
- LOG_LEVEL - Enum // Different log levels
- LogMessageGenerator // Generating the log message
- Sink // saving to the destination
- LogProcessor // picking up the logs from the queue to process
- Log

Logger <#>-- sink(\*)
Logger <>-- LogProcessor

## Class Design

```
class LogManager:
    - Map<String, Logger> loggers
    - LogProcessor processor
    - INSTANCE: LogManager
    + getInstance() : LogManager
    + getLogger(String) : Logger
    + createLogger(String) : Logger
    + shutdown() : void
    + getProcessor() : AsyncLogProcessor


Class Logger:
    - processor
    - sink[]
    + getInstance() : Logger
    + registerLog(LogLevel level, String message)
    + addSink(String name, Sink sink)
    + close()

Class Log:
    - String message
    - TemporalAccessor time
    - String threadName

Enum LogLevel:
    TRACE
    INFO
    DEBUG
    ERROR
    CRITICAL

//
Class AsyncLogProcessor:
    - queue : Queue<Log>
    + process(Log log, Sink sink)

Interface Sink:
    + processLog(Log log)

class FileSink:
    - String filePath
    + processLog(Log log)

class ConsoleSink:
    - processLog(Log log)
```
