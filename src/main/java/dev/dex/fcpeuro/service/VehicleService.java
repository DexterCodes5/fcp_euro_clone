package dev.dex.fcpeuro.service;

import dev.dex.fcpeuro.entity.vehicle.*;
import dev.dex.fcpeuro.entity.vehicle.vehiclebody.*;
import dev.dex.fcpeuro.entity.vehicle.vehiclebodyengine.*;
import dev.dex.fcpeuro.entity.vehicle.vehiclebodyenginetransmission.*;
import dev.dex.fcpeuro.exc.*;
import dev.dex.fcpeuro.repo.vehicle.*;
import lombok.*;
import org.springframework.stereotype.*;

import java.util.*;
import java.util.stream.*;

@Service
@RequiredArgsConstructor
public class VehicleService {
    private final MakeRepository makeRepository;
    private final BaseVehicleRepository baseVehicleRepository;
    private final VehicleRepository vehicleRepository;
    private final BodyRepository bodyRepository;
    private final VehicleBodyRepository vehicleBodyRepository;
    private final VehicleBodyEngineRepository vehicleBodyEngineRepository;
    private final EngineRepository engineRepository;
    private final VehicleBodyEngineTransmissionRepository vehicleBodyEngineTransmissionRepository;
    private final TransmissionRepository transmissionRepository;

    public List<Integer> findYears() {
        return baseVehicleRepository.findAll().stream()
                .map(BaseVehicle::getYear)
                .distinct()
                .toList();
    }

    public List<Make> findMakesByYear(int year) {
        // find all baseVehicles for this year
        List<BaseVehicle> baseVehicles = baseVehicleRepository.findByYear(year);
        List<Integer> makeIds = baseVehicles.stream().map(BaseVehicle::getMakeId).distinct().toList();
        return makeRepository.findAllById(makeIds);
    }

    public List<BaseVehicle> findBaseVehiclesByYearAndMakeId(int year, int makeId) {
        return baseVehicleRepository.findByYearAndMakeId(year, makeId);
    }

    public List<Vehicle> findVehiclesByBaseVehicleId(int baseVehicleId) {
        return vehicleRepository.findByBaseVehicleId(baseVehicleId);
    }

    public List<Body> findBodiesByVehicleId(int vehicleId) {
        List<VehicleBody> vehicleBodies = vehicleBodyRepository.findByVehicleId(vehicleId);
        List<Integer> bodyIds = vehicleBodies.stream().map(VehicleBody::getBodyId).distinct().toList();
        return bodyRepository.findAllById(bodyIds);
    }

    public List<Engine> findEnginesByVehicleIdAndBodyId(int vehicleId, int bodyId) {
        List<VehicleBodyEngine> vehicleBodyEngines = vehicleBodyEngineRepository.findByVehicleIdAndBodyId(vehicleId, bodyId);
        List<Integer> engineIds = vehicleBodyEngines.stream().map(VehicleBodyEngine::getEngineId).distinct().toList();
        return engineRepository.findAllById(engineIds);
    }

    public List<Object> findTransmissionsByVehicleIdBodyIdAndEngineId(int vehicleId, int bodyId, int engineId) {
        List<VehicleBodyEngineTransmission> vehicleBodyEngineTransmissions = vehicleBodyEngineTransmissionRepository
                .findByVehicleIdAndBodyIdAndEngineId(vehicleId, bodyId, engineId);
        List<Integer> transmissionIds = vehicleBodyEngineTransmissions.stream()
                .map(VehicleBodyEngineTransmission::getTransmissionId)
                .distinct()
                .toList();
        List<Transmission> transmissions = transmissionRepository.findAllById(transmissionIds);

        String transmissionIdsStr = transmissionIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
        Transmission.TransmissionNotSure transmissionNotSure =
                new Transmission.TransmissionNotSure(transmissionIdsStr, "Not sure");
        List<Object> res = new ArrayList<>(transmissions);
        res.add(0, transmissionNotSure);
        return res;
    }

    public void isVehicleValid(String make, String model, int year, int vehicleId, int bodyId, int engineId,
                               List<Integer> transmissionIds) {
        Make makeEntity = makeRepository.findByMake(make)
                .orElseThrow(() -> new ResourceNotFoundException("Make " + make + " not found"));
        BaseVehicle baseVehicleEntity = baseVehicleRepository.findByYearAndMakeIdAndModel(year, makeEntity.getId(),
                model)
                .orElseThrow(() -> new ResourceNotFoundException("BaseVehicle with year=" + year
                        + ", makeId=" + makeEntity.getId() + ", model=" + model + " not found"));
        vehicleRepository.findByIdAndBaseVehicleId(vehicleId, baseVehicleEntity.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle with id=" + vehicleId
                + ", baseVehicleId=" + baseVehicleEntity.getId() + " not found"));
        List<VehicleBodyEngineTransmission> vehicleBodyEngineTransmissions = vehicleBodyEngineTransmissionRepository
                .findByVehicleIdAndBodyIdAndEngineIdAndTransmissionIdIn(vehicleId, bodyId, engineId, transmissionIds);
        if (vehicleBodyEngineTransmissions.size() < transmissionIds.size()) {
            throw new ResourceNotFoundException("VehicleBodyEngineTransmission with ids=" + transmissionIds + " not found");
        }
    }
}
