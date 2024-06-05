package dev.dex.fcpeuro.repo.vehicle;

import dev.dex.fcpeuro.entity.vehicle.vehiclebody.*;
import org.springframework.data.jpa.repository.*;

import java.util.*;

public interface VehicleBodyRepository extends JpaRepository<VehicleBody, VehicleBodyId> {
    List<VehicleBody> findByVehicleId(int vehicleId);
}
