package dev.dex.fcpeuro.model.customerorder;

import dev.dex.fcpeuro.entity.customerorder.*;
import jakarta.validation.constraints.*;

import java.util.*;

public record CustomerOrderRequest(
        @Email
        String email,
        @NotBlank
        String payment,
        @Positive
        Double subtotal,
        @PositiveOrZero
        Double shipping,
        @PositiveOrZero
        Double tax,
        @Positive
        Double total,
        @NotNull
        List<CartItemRequest> cartItems,
        @NotNull
        Address address,
        @NotNull
        DeliveryRequest delivery
) {
}
