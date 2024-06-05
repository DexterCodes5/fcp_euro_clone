package dev.dex.fcpeuro.controller;

import dev.dex.fcpeuro.model.*;
import dev.dex.fcpeuro.service.*;
import lombok.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryResponse> getCategories(@RequestParam int vehicleId,
                                                @RequestParam(required = false) String category,
                                                @RequestParam(required = false) String quality,
                                                @RequestParam(required = false) String brand,
                                                @RequestParam(required = false) String searchOnly) {
        return categoryService.getCategories(category, vehicleId, quality, brand, searchOnly);
    }
}
