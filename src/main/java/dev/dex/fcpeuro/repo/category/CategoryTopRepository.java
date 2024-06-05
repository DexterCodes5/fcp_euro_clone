package dev.dex.fcpeuro.repo.category;

import dev.dex.fcpeuro.entity.category.*;
import org.springframework.data.jpa.repository.*;

import java.util.*;

public interface CategoryTopRepository extends JpaRepository<CategoryTop, Integer> {
    Optional<CategoryTop> findByCategoryTop(String categoryTop);
}
