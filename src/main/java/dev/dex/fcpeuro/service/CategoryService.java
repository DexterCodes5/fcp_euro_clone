package dev.dex.fcpeuro.service;

import dev.dex.fcpeuro.entity.*;
import dev.dex.fcpeuro.entity.category.*;
import dev.dex.fcpeuro.model.*;
import dev.dex.fcpeuro.util.*;
import lombok.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final PartService partService;

    public List<CategoryResponse> getCategories(String category, int vehicleId, String quality, String brand,
                                                String searchOnly) {
        List<Part> parts = partService.findByCategoryAndVehicleIdAndQualityAndBrandAndSearchOnly(category, vehicleId,
                quality, brand, searchOnly);
        List<CategoryBot> categoriesBot = parts.stream().map(Part::getCategoryBot).toList();
        return CategoryUtil.createCategoryResponse(categoriesBot);
    }
}
