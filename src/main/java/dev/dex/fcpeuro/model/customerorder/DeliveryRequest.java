package dev.dex.fcpeuro.model.customerorder;

import java.time.*;

public record DeliveryRequest(
        Boolean receiveTextNotification,
        Double price,
        LocalDate receiveDate
) {
}
