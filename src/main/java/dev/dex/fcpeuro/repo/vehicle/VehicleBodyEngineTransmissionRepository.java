package dev.dex.fcpeuro.repo.vehicle;

import dev.dex.fcpeuro.entity.vehicle.vehiclebodyenginetransmission.*;
import org.springframework.data.jpa.repository.*;

import java.util.*;

public interface VehicleBodyEngineTransmissionRepository extends JpaRepository<VehicleBodyEngineTransmission, VehicleBodyEngineTransmissionId> {
    List<VehicleBodyEngineTransmission> findByVehicleIdAndBodyIdAndEngineId(int vehicleId, int bodyId, int engineId);

    List<VehicleBodyEngineTransmission> findByVehicleIdAndBodyIdAndEngineIdAndTransmissionIdIn(
            int vehicleId, int bodyId, int engineId, List<Integer> transmissionIds);
}
