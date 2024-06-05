package dev.dex.fcpeuro.service;

import dev.dex.fcpeuro.entity.*;
import dev.dex.fcpeuro.exc.*;
import dev.dex.fcpeuro.model.*;
import dev.dex.fcpeuro.repo.*;
import lombok.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PartService {
    private final PartRepository partRepository;

    public List<PartResponse> getPartResponses(String category, int vehicleId, String quality, String brand,
                                               String searchOnly, String order) {
        List<Part> parts = null;
        if (order == null) {
            parts = findByCategoryAndVehicleIdAndQualityAndBrandAndSearchOnly(category, vehicleId, quality, brand,
                    searchOnly);
        } else if (order.equals("ascend_by_price")) {
            Boolean kit = getKit(searchOnly);
            parts = partRepository.findByCategoryAndVehicleIdAndQualityAndBrandAndKitOrderByAsc(category, vehicleId,
                    quality, brand, kit);
        } else if (order.equals("descend_by_price")) {
            Boolean kit = getKit(searchOnly);
            parts = partRepository.findByCategoryAndVehicleIdAndQualityAndBrandAndKitOrderByDesc(category, vehicleId,
                    quality, brand, kit);
        }
        return mapPartsToPartResponses(parts);
    }

    public List<Part> findByCategoryAndVehicleIdAndQualityAndBrandAndSearchOnly(String category, int vehicleId, String quality,
                                                                                String brand, String searchOnly) {
        Boolean kit = getKit(searchOnly);
        return partRepository.findByCategoryAndVehicleIdAndQualityAndBrandAndKit(category, vehicleId, quality, brand, kit);
    }

    private Boolean getKit(String searchOnly) {
        Boolean kit;
        if (searchOnly == null) {
            kit = null;
        } else if (searchOnly.equals("kits")) {
            kit = true;
        } else {
            kit = false;
        }
        return kit;
    }

    private List<PartResponse> mapPartsToPartResponses(List<Part> parts) {
        return parts.stream()
                .map(PartResponse::new)
                .toList();
    }

    public PartResponse findByUrl(String url) {
        return partRepository.findByUrl(url)
                .map(p -> {
                    p.getKitParts().sort((p1, p2) -> p1.getId().compareTo(p2.getId()));
                    return new PartResponse(p);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Url not found " + url));
    }

    public PartResponse findById(int id) {
        return partRepository.findById(id)
                .map(PartResponse::new)
                .orElseThrow(() -> new ResourceNotFoundException("Part id not found " + id));
    }

}
