package dev.dex.fcpeuro.repo;

import dev.dex.fcpeuro.entity.*;
import org.springframework.data.jpa.repository.*;

import java.util.*;

public interface PartRepository extends JpaRepository<Part, Integer> {

    Optional<Part> findByUrl(String url);

    @Query("SELECT p FROM Part p JOIN p.vehicles v WHERE (v.id = :vehicleId OR p.universal = true)" +
            " AND (:category IS NULL OR p.categoryBot.categoryBot = :category" +
            " OR p.categoryBot.categoryMid.categoryMid = :category" +
            " OR p.categoryBot.categoryMid.categoryTop.categoryTop = :category)" +
            " AND (:quality IS NULL OR p.quality = :quality)" +
            " AND (:brand IS NULL OR p.brand.name = :brand)" +
            " AND (:kit IS NULL OR p.kit = :kit)"
    )
    List<Part> findByCategoryAndVehicleIdAndQualityAndBrandAndKit(String category, Integer vehicleId, String quality, String brand,
                                                            Boolean kit);

    @Query("SELECT p FROM Part p JOIN p.vehicles v WHERE (v.id = :vehicleId OR p.universal = true)" +
            " AND (:category IS NULL OR p.categoryBot.categoryBot = :category" +
            " OR p.categoryBot.categoryMid.categoryMid = :category" +
            " OR p.categoryBot.categoryMid.categoryTop.categoryTop = :category)" +
            " AND (:quality IS NULL OR p.quality = :quality)" +
            " AND (:brand IS NULL OR p.brand.name = :brand)" +
            " AND (:kit IS NULL OR p.kit = :kit)" +
            " ORDER BY p.price ASC"
    )
    List<Part> findByCategoryAndVehicleIdAndQualityAndBrandAndKitOrderByAsc(String category, int vehicleId, String quality,
                                                                            String brand, Boolean kit);

    @Query("SELECT p FROM Part p JOIN p.vehicles v WHERE (v.id = :vehicleId OR p.universal = true)" +
            " AND (:category IS NULL OR p.categoryBot.categoryBot = :category" +
            " OR p.categoryBot.categoryMid.categoryMid = :category" +
            " OR p.categoryBot.categoryMid.categoryTop.categoryTop = :category)" +
            " AND (:quality IS NULL OR p.quality = :quality)" +
            " AND (:brand IS NULL OR p.brand.name = :brand)" +
            " AND (:kit IS NULL OR p.kit = :kit)" +
            " ORDER BY p.price DESC"
    )
    List<Part> findByCategoryAndVehicleIdAndQualityAndBrandAndKitOrderByDesc(String category, int vehicleId, String quality,
                                                                             String brand, Boolean kit);
}
