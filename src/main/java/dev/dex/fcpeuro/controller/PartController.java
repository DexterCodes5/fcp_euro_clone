package dev.dex.fcpeuro.controller;

import dev.dex.fcpeuro.service.PartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/parts")
@RequiredArgsConstructor
public class PartController {
    private final PartService partService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(partService.findAll());
    }

    @GetMapping("/{vehicleId}")
    public ResponseEntity<?> getByVehicle(@PathVariable int vehicleId) {
        return ResponseEntity.ok(partService.findByVehicleId(vehicleId));
    }

    @GetMapping("/get-part-by-url/{url}")
    public ResponseEntity<?> getByUrl(@PathVariable String url) {
        System.out.println("Here");
        return ResponseEntity.ok(partService.findByUrl(url));
    }
}
