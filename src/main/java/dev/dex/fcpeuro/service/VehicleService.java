package dev.dex.fcpeuro.service;

import dev.dex.fcpeuro.entity.Vehicle;
import dev.dex.fcpeuro.repo.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleService {
    private final VehicleRepository vehicleRepository;

    public List<Vehicle> findAll() {
        return vehicleRepository.findAll();
    }
}
