package dev.dex.fcpeuro.repo.vehicle;

import dev.dex.fcpeuro.entity.vehicle.vehiclebodyengine.*;
import org.springframework.data.jpa.repository.*;

import java.util.*;

public interface VehicleBodyEngineRepository extends JpaRepository<VehicleBodyEngine, VehicleBodyEngineId> {
    List<VehicleBodyEngine> findByVehicleIdAndBodyId(int vehicleId, int bodyId);

    List<VehicleBodyEngine> findByEngineIdIn(List<Integer> engineIds);
}
