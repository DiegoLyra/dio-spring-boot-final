package dio.budgeting.infrastructure.http.response;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public record ErrorResponseDTO(
    int status,
    String error,
    String message,
    LocalDateTime timestamp
) {
    public ErrorResponseDTO(int status, String error, String message) {
        this(status, error, message, LocalDateTime.now(ZoneOffset.UTC));
    }
}