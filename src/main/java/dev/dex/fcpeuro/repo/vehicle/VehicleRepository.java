package dev.dex.fcpeuro.repo.vehicle;

import dev.dex.fcpeuro.entity.vehicle.*;
import org.springframework.data.jpa.repository.*;

import java.util.*;

public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {
    List<Vehicle> findByBaseVehicleId(int baseVehicleId);

    Optional<Vehicle> findByIdAndBaseVehicleId(int id, int baseVehicleId);
}
