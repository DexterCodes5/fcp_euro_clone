package dev.dex.fcpeuro.model;

public record RecaptchaResponse(
        Boolean success,
        String challenge_ts,
        String hostname,
        Double score,
        String action
) {
}
