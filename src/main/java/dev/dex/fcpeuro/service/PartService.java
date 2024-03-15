package dev.dex.fcpeuro.service;

import dev.dex.fcpeuro.entity.Part;
import dev.dex.fcpeuro.repo.PartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PartService {
    private final PartRepository partRepository;

    public List<Part> findAll() {
        return partRepository.findAll();
    }

    public List<Part> findByVehicleId(int vehicleId) {
        return partRepository.findByVehiclesId(vehicleId);
    }

    public Part findByUrl(String url) {
        return partRepository.findByUrl(url)
                .orElseThrow(() -> new RuntimeException("Invalid url"));
    }
}
