package dev.dex.fcpeuro.repo.category;

import dev.dex.fcpeuro.entity.category.*;
import org.springframework.data.jpa.repository.*;

import java.util.*;

public interface CategoryMidRepository extends JpaRepository<CategoryMid, Integer> {
    Optional<CategoryMid> findByCategoryMid(String categoryMid);
}
