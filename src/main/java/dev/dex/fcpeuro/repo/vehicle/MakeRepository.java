package dev.dex.fcpeuro.repo.vehicle;

import dev.dex.fcpeuro.entity.vehicle.*;
import org.springframework.data.jpa.repository.*;

import java.util.*;

public interface MakeRepository extends JpaRepository<Make, Integer> {
    Optional<Make> findByMake(String make);
}
