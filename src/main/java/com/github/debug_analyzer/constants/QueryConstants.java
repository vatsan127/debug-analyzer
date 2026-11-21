package com.github.debug_analyzer.constants;

public final class QueryConstants {

    public static final String FETCH_UNPROCESSED_ERROR_LOGS = """
            SELECT *
            FROM error_logs
            WHERE fingerprint = '' AND application != ?
            ORDER BY timestamp
            LIMIT ?
            """;

    private QueryConstants() {
    }
}
