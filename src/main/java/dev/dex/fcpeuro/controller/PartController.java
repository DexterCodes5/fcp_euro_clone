package dev.dex.fcpeuro.controller;

import dev.dex.fcpeuro.model.*;
import dev.dex.fcpeuro.service.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/parts")
@RequiredArgsConstructor
public class PartController {
    private final PartService partService;

    @GetMapping
    public List<PartResponse> getParts(@RequestParam int vehicleId,
                                       @RequestParam(required = false) String category,
                                       @RequestParam(required = false) String quality,
                                       @RequestParam(required = false) String brand,
                                       @RequestParam(name = "search_only", required = false) String searchOnly,
                                       @RequestParam(required = false) String order) {
        return partService.getPartResponses(category, vehicleId, quality, brand, searchOnly, order);
    }

    @GetMapping("/get-part-by-url/{url}")
    public ResponseEntity<?> getByUrl(@PathVariable String url) {
        return ResponseEntity.ok(partService.findByUrl(url));
    }

    @GetMapping("/get-part-by-id/{id}")
    public PartResponse getPartById(@PathVariable int id) {
        return partService.findById(id);
    }
}
