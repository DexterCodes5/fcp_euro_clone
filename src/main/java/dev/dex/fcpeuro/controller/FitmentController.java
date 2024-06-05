package dev.dex.fcpeuro.controller;

import dev.dex.fcpeuro.model.fitment.*;
import dev.dex.fcpeuro.service.*;
import lombok.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/fitment")
@RequiredArgsConstructor
public class FitmentController {
    private final FitmentService fitmentService;

    @GetMapping("/does-part-fit-vehicle")
    public Boolean doesPartFitVehicle(@RequestParam int partId, @RequestParam int vehicleId) {
        return fitmentService.doesPartFitVehicle(partId, vehicleId);
    }

    @GetMapping
    public List<FitmentResponse> fitment(@RequestParam int partId) {
        return fitmentService.fitment(partId);
    }
}
