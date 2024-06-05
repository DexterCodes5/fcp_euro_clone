package dev.dex.fcpeuro.service;

import dev.dex.fcpeuro.entity.*;
import dev.dex.fcpeuro.entity.vehicle.*;
import dev.dex.fcpeuro.exc.*;
import dev.dex.fcpeuro.model.fitment.*;
import dev.dex.fcpeuro.repo.*;
import dev.dex.fcpeuro.repo.vehicle.*;
import lombok.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
@RequiredArgsConstructor
public class FitmentService {
    private final PartRepository partRepository;
    private final BaseVehicleRepository baseVehicleRepository;
    private final MakeRepository makeRepository;

    public Boolean doesPartFitVehicle(int partId, int vehicleId) {
        List<Vehicle> vehicles = partRepository.findById(partId)
                .map(Part::getVehicles)
                .orElse(null);
        if (vehicles == null) {
            return false;
        }
        for (var vehicle: vehicles) {
            if (vehicle.getId() == vehicleId) {
                return true;
            }
        }
        return false;
    }

    public List<FitmentResponse> fitment(int partId) {
        // Get baseVehicles
        List<Vehicle> vehicles = partRepository.findById(partId)
                .map(Part::getVehicles)
                .orElseThrow(() -> new ResourceNotFoundException("Part with id "  + partId + " doesn't exist"));

        List<Integer> baseVehicleIds = vehicles.stream()
                .map(Vehicle::getBaseVehicleId)
                .distinct()
                .toList();

        List<BaseVehicle> baseVehicles = baseVehicleRepository.findAllById(baseVehicleIds);
        List<FitmentVehicle> fitmentVehiclesSorted = baseVehicles.stream()
                .map(baseVehicle -> {
                    Make make = makeRepository.findById(baseVehicle.getMakeId())
                            .orElseThrow(() -> new ResourceNotFoundException("Invalid make id: " + baseVehicle.getMakeId()));
                    return new FitmentVehicle(baseVehicle.getId(), baseVehicle.getYear(), make.getMake(),
                            baseVehicle.getModel(), "");
                })
                .sorted((o1, o2) -> {
                    if (o1.model().equals(o2.model())) {
                        return o1.year() - o2.year();
                    }
                    return o1.model().compareTo(o2.model());
                })
                .toList();
        return createFitmentResponse(fitmentVehiclesSorted);
    }

    private List<FitmentResponse> createFitmentResponse(List<FitmentVehicle> fitmentVehiclesSorted) {
        FitmentResponse fitmentResponse = new FitmentResponse(
                "",
                "",
                new ArrayList<>());
        List<FitmentResponse> res = new ArrayList<>();

        for (FitmentVehicle fitmentVehicle : fitmentVehiclesSorted) {
            if (!fitmentResponse.getModel().equals(fitmentVehicle.model())) {
                fitmentResponse = new FitmentResponse(fitmentVehicle.make(), fitmentVehicle.model(), new ArrayList<>());
                res.add(fitmentResponse);
            }
            fitmentResponse.getVehicles().add(fitmentVehicle);
        }
        return res;
    }
}
