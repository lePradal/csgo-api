package com.prads.csgoapi.model.entity;

import java.util.List;
import java.util.UUID;
import com.prads.csgoapi.enumerator.MapPool;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "maps")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MapEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", updatable = false, unique = true, nullable = false)
  private UUID id;

  @Column(name = "name", updatable = true, unique = true, nullable = false)
  private String name;

  @Column(name = "map_pool", updatable = true, unique = false, nullable = false)
  private List<MapPool> mapPool;
}
