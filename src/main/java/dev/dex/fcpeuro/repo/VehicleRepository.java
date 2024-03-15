package dev.dex.fcpeuro.repo;

import dev.dex.fcpeuro.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {
}
