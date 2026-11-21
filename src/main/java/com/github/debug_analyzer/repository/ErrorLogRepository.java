package com.github.debug_analyzer.repository;

import com.github.debug_analyzer.model.ErrorLog;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.github.debug_analyzer.constants.QueryConstants.FETCH_UNPROCESSED_ERROR_LOGS;

@Repository
public class ErrorLogRepository {

    private final DataSource dataSource;

    public ErrorLogRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<ErrorLog> fetchUnprocessed(String self, int limit) throws SQLException {
        List<ErrorLog> errorLogs = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FETCH_UNPROCESSED_ERROR_LOGS)) {
            statement.setString(1, self);
            statement.setInt(2, limit);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    ErrorLog errorLog = new ErrorLog();
                    errorLog.setThreadName(rs.getString("thread_name"));
                    errorLog.setApplication(rs.getString("application"));
                    errorLog.setInstanceName(rs.getString("instance_name"));
                    errorLog.setLogLevel(rs.getString("log_level"));
                    errorLog.setTimestamp(rs.getObject("timestamp", LocalDateTime.class));
                    errorLog.setSequenceNumber(rs.getLong("sequence_number"));
                    errorLog.setTraceId(rs.getString("trace_id"));
                    errorLog.setLogDetails(rs.getString("log_details"));
                    errorLogs.add(errorLog);
                }
            }
        }
        return errorLogs;
    }
}
