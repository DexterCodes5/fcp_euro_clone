package dev.dex.fcpeuro.repo.vehicle;

import dev.dex.fcpeuro.entity.vehicle.*;
import org.springframework.data.jpa.repository.*;

import java.util.*;

public interface BaseVehicleRepository extends JpaRepository<BaseVehicle, Integer> {
    List<BaseVehicle> findByYear(int year);

    List<BaseVehicle> findByYearAndMakeId(int year, int makeId);

    Optional<BaseVehicle> findByYearAndMakeIdAndModel(int year, Integer id, String model);
}
