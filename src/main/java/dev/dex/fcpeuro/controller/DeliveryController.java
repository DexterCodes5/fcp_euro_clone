package dev.dex.fcpeuro.controller;

import dev.dex.fcpeuro.model.*;
import dev.dex.fcpeuro.service.*;
import lombok.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/deliveries")
@RequiredArgsConstructor
public class DeliveryController {
    private final DeliveryService deliveryService;

    @PostMapping
    public Object calculateDeliveries(@RequestBody Address address) {
        return deliveryService.calculateDeliveryMethods(address);
    }
}
