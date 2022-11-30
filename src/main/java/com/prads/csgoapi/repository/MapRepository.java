package com.prads.csgoapi.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.prads.csgoapi.model.entity.MapEntity;

public interface MapRepository extends JpaRepository<MapEntity, UUID> {

  Optional<MapEntity> findById(UUID id);

  Optional<MapEntity> findByName(String name);
}
