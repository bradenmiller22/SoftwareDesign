# LogAnalyzer

## Problem Statement
Implement a multithreaded log processing system that categorizes log entries into `INFO`, `WARN`, and `ERROR` messages. Log reader threads read and distribute logs into buffers, while analyzer threads consume from these buffers to summarize logs using a thread pool.

## Developer Documentation

### 1. LogBuffer
- **Variables**:
  - `infoBuffer`, `warnBuffer`, `errorBuffer` (ArrayBlockingQueue)
  - `end (String)`: termination signal

### 2. LogReader (implements Runnable)
- **Variables**: 
  - `logs (List<String>)`, `logBuffer (LogBuffer)`
- **Method**: 
  - `run()`: distributes logs to correct buffer by prefix

### 3. ErrorAnalyzer (implements Runnable)
- **Tracks**: 
  - Error log count, writes to `errors_only.log`

### 4. InfoAnalyzer (implements Runnable)
- **Tracks**:
  - Login counts per user from `INFO` logs

### 5. WarnAnalyzer (implements Runnable)
- **Tracks**:
  - Unauthorized access attempts from `WARN` logs

### 6. LogProcessorTest (Driver)
- Sets up thread pool, reads logs in batches of 100, and runs all analyzers

## UML Diagram
![UML](https://github.com/bradenmiller22/SoftwareDesign/blob/main/LogAnalyzer/doc/LogAnalyzerUML.png)

## Java Docs
[View Java Docs](http://localhost:8000/bmiller38_swd)

## User Documentation

### Run the Program
1. Place input file at: `LogAnalyzer/large_log_file.log`
2. Open and run `LogProcessorTest.java`
3. View console summary and verify `errors_only.log` is created

## Source Code
[View Code](https://github.com/bradenmiller22/SoftwareDesign/tree/main/LogAnalyzer/src)
