package dev.dex.fcpeuro.repo;

import dev.dex.fcpeuro.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Integer> {
    List<Image> findByPartId(Integer partId);

    Optional<Image> findByName(String name);
}
