package dev.dex.fcpeuro.repo;

import dev.dex.fcpeuro.entity.Part;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PartRepository extends JpaRepository<Part, Integer> {

    List<Part> findByVehiclesId(int vehicleId);

    Optional<Part> findByUrl(String url);
}
