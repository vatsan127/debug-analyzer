package com.github.debug_analyzer.poller;

import com.github.debug_analyzer.model.ErrorLog;
import com.github.debug_analyzer.repository.ErrorLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class ErrorLogPoller implements ApplicationRunner {

    private static final int BATCH_SIZE = 100;
    private static final long SLEEP_SECONDS = 30;

    private final ErrorLogRepository errorLogRepository;
    private final String applicationName;

    public ErrorLogPoller(ErrorLogRepository errorLogRepository,
                          @Value("${spring.application.name}") String applicationName) {
        this.errorLogRepository = errorLogRepository;
        this.applicationName = applicationName;
    }

    @Override
    public void run(ApplicationArguments args) throws InterruptedException {
        while (true) {
            try {
                List<ErrorLog> errorLogs = errorLogRepository.fetchUnprocessed(applicationName, BATCH_SIZE);
                for (ErrorLog errorLog : errorLogs) {
                    log.info("Unprocessed error - application={}, traceId={}, thread={}, sequence={}, timestamp={}",
                            errorLog.getApplication(), errorLog.getTraceId(), errorLog.getThreadName(),
                            errorLog.getSequenceNumber(), errorLog.getTimestamp());
                }
            } catch (SQLException e) {
                log.error("Fetching unprocessed error logs failed", e);
            }
            TimeUnit.SECONDS.sleep(SLEEP_SECONDS);
        }
    }
}
