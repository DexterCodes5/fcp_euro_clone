package dev.dex.fcpeuro.repo;

import dev.dex.fcpeuro.entity.*;
import org.springframework.data.jpa.repository.*;

import java.util.*;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findByPartId(Integer partId);
}
