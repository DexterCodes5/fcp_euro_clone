package dev.dex.fcpeuro.controller;

import dev.dex.fcpeuro.model.customerorder.*;
import dev.dex.fcpeuro.service.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer-orders")
@RequiredArgsConstructor
public class CustomerOrderController {
    private final CustomerOrderService customerOrderService;

    @PostMapping("/receive-order-request-guest")
    public ResponseEntity<?> receiveOrderRequestGuest(@RequestBody CustomerOrderRequest customerOrderRequest) {
        customerOrderService.receiveOrderGuest(customerOrderRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/receive-order-request")
    public ResponseEntity<?> receiveOrderRequest(@RequestBody CustomerOrderRequest customerOrderRequest) {
        customerOrderService.receiveOrder(customerOrderRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
