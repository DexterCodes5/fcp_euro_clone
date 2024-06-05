package dev.dex.fcpeuro.controller;

import dev.dex.fcpeuro.service.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/vehicles")
@RequiredArgsConstructor
public class VehicleController {
    private final VehicleService vehicleService;

    @GetMapping("/years")
    public List<Integer> getYears() {
        return vehicleService.findYears();
    }

    @GetMapping("/makes")
    public ResponseEntity<?> getMakesByYear(@RequestParam int year) {
        return ResponseEntity.ok(vehicleService.findMakesByYear(year));
    }

    @GetMapping("/base_vehicles")
    public ResponseEntity<?> getBaseVehiclesByYearAndMake(@RequestParam int year, @RequestParam int make) {
        return ResponseEntity.ok(vehicleService.findBaseVehiclesByYearAndMakeId(year, make));
    }

    @GetMapping("/vehicles")
    public ResponseEntity<?> getVehiclesByBaseVehicleId(@RequestParam int baseVehicleId) {
        return ResponseEntity.ok(vehicleService.findVehiclesByBaseVehicleId(baseVehicleId));
    }

    @GetMapping("/bodies")
    public ResponseEntity<?> getBodiesByVehicleId(@RequestParam int vehicleId) {
        return ResponseEntity.ok(vehicleService.findBodiesByVehicleId(vehicleId));
    }

    @GetMapping("/engines")
    public ResponseEntity<?> getEnginesByVehicleIdAndBodyId(@RequestParam int vehicleId, @RequestParam int bodyId) {
        return ResponseEntity.ok(vehicleService.findEnginesByVehicleIdAndBodyId(vehicleId, bodyId));
    }

    @GetMapping("/transmissions")
    public ResponseEntity<?> getTransmissionsByVehicleIdBodyIdAndEngineId(@RequestParam int vehicleId,
                                                                          @RequestParam int bodyId,
                                                                          @RequestParam int engineId) {
        return ResponseEntity.ok(vehicleService.findTransmissionsByVehicleIdBodyIdAndEngineId(vehicleId, bodyId, engineId));
    }

    @PostMapping("/is-vehicle-valid")
    public ResponseEntity<?> isVehicleValid(@RequestParam String make, @RequestParam String model,
                                            @RequestParam int year, @RequestParam int vehicleId,
                                            @RequestParam int bodyId, @RequestParam int engineId,
                                            @RequestParam List<Integer> transmissionIds) {
        vehicleService.isVehicleValid(make, model, year, vehicleId, bodyId, engineId, transmissionIds);
        return ResponseEntity.ok(null);
    }
}
