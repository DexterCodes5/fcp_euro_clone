package dev.dex.fcpeuro.exc;

import java.time.*;

public record ApiError(
        String path,
        String message,
        int statusCode,
        LocalDateTime localDateTime
) {
}
