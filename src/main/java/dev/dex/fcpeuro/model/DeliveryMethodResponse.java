package dev.dex.fcpeuro.model;

import java.time.*;

public record DeliveryMethodResponse(
        Double price,
        LocalDate receiveDate
) {
}
