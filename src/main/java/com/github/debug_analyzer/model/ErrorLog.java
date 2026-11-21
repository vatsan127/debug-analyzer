package com.github.debug_analyzer.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ErrorLog {
    private String threadName;
    private String application;
    private String instanceName;
    private String logLevel;
    private LocalDateTime timestamp;
    private long sequenceNumber;
    private String traceId;
    private String logDetails;
}
