package com.prads.csgoapi.controller;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.prads.csgoapi.enumerator.MapPool;
import com.prads.csgoapi.model.dto.MapDTO;
import com.prads.csgoapi.service.MapService;
import com.prads.csgoapi.utils.Constants;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.extern.log4j.Log4j2;

@Validated
@RestController
@RequestMapping("/v1")
@Log4j2
public class MapController {

  @Autowired
  private MapService service;

  @GetMapping("/maps/{mapId}")
  public ResponseEntity<MapDTO> getMapById(@PathVariable UUID mapId) {
    log.info("GET /v1/maps/{mapId} - Getting map by ID.");

    MapDTO response = service.getMap(mapId);

    log.info("GET /v1/maps/{mapId} - Response OK.");
    return ResponseEntity.ok(response);
  }

  @GetMapping("/maps")
  public ResponseEntity<List<MapDTO>> getMaps() {
    log.info("GET /v1/maps - Getting map list.");

    List<MapDTO> response = service.getMaps();

    log.info("GET /v1/maps - Response OK.");
    return ResponseEntity.ok(response);
  }

  @GetMapping("/maps:by_pool")
  public ResponseEntity<List<MapDTO>> getMapsByPool(
      @RequestHeader(name = Constants.X_MAP_POOL, required = true) MapPool pool) {
    log.info("GET /v1/valve_maps - Getting valve map list.");

    List<MapDTO> response = service.getMapsByPool(pool);

    log.info("GET /v1/maps_by_pool - Response OK.");
    return ResponseEntity.ok(response);
  }

  @PostMapping("/maps")
  public ResponseEntity<List<MapDTO>> createMaps(
      @NotEmpty(message = "Maps cannot be empty.") @RequestBody List<@Valid MapDTO> maps) {
    log.info("POST /v1/maps - Creating maps.");

    List<MapDTO> response = service.createMaps(maps);

    log.info("POST /v1/maps - Response CREATED.");
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PatchMapping("/maps/{mapId}")
  public ResponseEntity<MapDTO> updateMap(@PathVariable UUID mapId,
      @RequestBody @Valid List<MapPool> pool) {
    log.info("PATCH /v1/maps/{mapId} - Updating map pool.");

    MapDTO response = service.updatePool(mapId, pool);

    log.info("PATCH /v1/maps/{mapId} - Response OK.");
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/maps/{mapId}")
  public ResponseEntity<MapDTO> deleteMap(@PathVariable UUID mapId) {
    log.info("DELETE /v1/maps/{mapId} - Deleting map.");

    service.deleteMap(mapId);

    log.info("DELETE /v1/maps/{mapId} - Response OK.");
    return ResponseEntity.ok().build();
  }

}
