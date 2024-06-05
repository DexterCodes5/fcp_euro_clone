package dev.dex.fcpeuro.repo.category;

import dev.dex.fcpeuro.entity.category.*;
import org.springframework.data.jpa.repository.*;

import java.util.*;

public interface CategoryBotRepository extends JpaRepository<CategoryBot, Integer> {
    Optional<CategoryBot> findByCategoryBot(String categoryBot);
}
