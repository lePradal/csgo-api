package com.prads.csgoapi.service;

import java.util.List;
import java.util.UUID;
import com.prads.csgoapi.enumerator.MapPool;
import com.prads.csgoapi.model.dto.MapDTO;

public interface MapService {
  MapDTO getMap(UUID id);

  List<MapDTO> getMaps();

  List<MapDTO> getMapsByPool(MapPool pool);

  List<MapDTO> createMaps(List<MapDTO> maps);

  MapDTO updatePool(UUID id, List<MapPool> pool);

  void deleteMap(UUID id);
}
