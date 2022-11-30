package com.prads.csgoapi.model.dto;

import java.util.List;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.prads.csgoapi.enumerator.MapPool;
import com.prads.csgoapi.model.entity.MapEntity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class MapDTO {
  @JsonProperty("id")
  private UUID id;

  @NotNull(message = "Name cannot be null.")
  @NotEmpty(message = "Name cannot be empty.")
  @JsonProperty("name")
  private String name;

  @NotNull(message = "Map pool cannot be null.")
  @Size(min = 1, message = "Map pool cannot be empty.")
  @JsonProperty("mapPool")
  private List<MapPool> mapPool;

  public static MapDTO fromEntity(MapEntity entity) {
    return MapDTO.builder().id(entity.getId()).name(entity.getName()).mapPool(entity.getMapPool())
        .build();
  }

  public MapEntity toEntity() {
    return MapEntity.builder().id(id).name(name).mapPool(mapPool).build();
  }
}
