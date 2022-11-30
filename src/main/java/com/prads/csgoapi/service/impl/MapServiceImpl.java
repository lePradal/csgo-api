package com.prads.csgoapi.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import com.prads.csgoapi.enumerator.MapPool;
import com.prads.csgoapi.exception.DataIntegrityException;
import com.prads.csgoapi.exception.InternalErrorException;
import com.prads.csgoapi.exception.NotFoundException;
import com.prads.csgoapi.model.dto.MapDTO;
import com.prads.csgoapi.model.entity.MapEntity;
import com.prads.csgoapi.repository.MapRepository;
import com.prads.csgoapi.service.MapService;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class MapServiceImpl implements MapService {

  private final String COULD_NOT_ACCESS_DATABASE = "Could not to access database.";
  private final String MAP_NOT_FOUND = "Map not found.";
  private final String NO_MAP_FOUND = "No map found.";

  @Autowired
  private MapRepository repository;

  @Override
  public MapDTO getMap(UUID id) {
    log.info("Getting map by id: {}", id);

    try {
      Optional<MapEntity> optional = repository.findById(id);

      if (optional.isEmpty()) {
        log.info(MAP_NOT_FOUND);
        throw new NotFoundException(MAP_NOT_FOUND);
      }

      MapEntity map = optional.get();

      return MapDTO.fromEntity(map);
    } catch (DataAccessException e) {
      log.error(COULD_NOT_ACCESS_DATABASE, e);
      throw new InternalErrorException(COULD_NOT_ACCESS_DATABASE);
    }
  }

  @Override
  public List<MapDTO> getMaps() {
    log.info("Getting maps.");

    try {
      List<MapEntity> maps = repository.findAll();

      if (maps.isEmpty()) {
        log.info(NO_MAP_FOUND);
        throw new NotFoundException(NO_MAP_FOUND);
      }

      return maps.stream().map(map -> MapDTO.fromEntity(map)).collect(Collectors.toList());

    } catch (DataAccessException e) {
      log.error(COULD_NOT_ACCESS_DATABASE, e);
      throw new InternalErrorException(COULD_NOT_ACCESS_DATABASE);
    }
  }

  @Override
  public List<MapDTO> getMapsByPool(MapPool pool) {
    log.info("Getting {} maps.", pool);

    try {
      List<MapEntity> maps = repository.findAll();

      if (maps.isEmpty()) {
        log.info(NO_MAP_FOUND);
        throw new NotFoundException(NO_MAP_FOUND);
      }

      return maps.stream().filter(map -> map.getMapPool().contains(pool))
          .map(map -> MapDTO.fromEntity(map)).collect(Collectors.toList());

    } catch (DataAccessException e) {
      log.error(COULD_NOT_ACCESS_DATABASE, e);
      throw new InternalErrorException(COULD_NOT_ACCESS_DATABASE);
    }
  }

  @Override
  public List<MapDTO> createMaps(List<MapDTO> maps) {
    log.info("Creating maps: {} total.", maps.size());

    try {

      List<MapEntity> entities =
          maps.stream().map(map -> map.toEntity()).collect(Collectors.toList());

      List<MapEntity> savedEntities = repository.saveAll(entities);

      return savedEntities.stream().map(map -> MapDTO.fromEntity(map)).collect(Collectors.toList());
    } catch (DataIntegrityViolationException e) {
      throw new DataIntegrityException(e.getMessage());
    } catch (DataAccessException e) {
      log.error(COULD_NOT_ACCESS_DATABASE, e);
      throw new InternalErrorException(COULD_NOT_ACCESS_DATABASE);
    }
  }

  @Override
  public MapDTO updatePool(UUID id, List<MapPool> pool) {
    log.info("Updating {} map pool.", id);

    try {
      Optional<MapEntity> optional = repository.findById(id);

      if (optional.isEmpty()) {
        log.info(MAP_NOT_FOUND);
        throw new NotFoundException(MAP_NOT_FOUND);
      }

      MapEntity entity = optional.get();
      entity.setMapPool(pool);

      MapEntity savedEntity = repository.save(entity);

      return MapDTO.fromEntity(savedEntity);
    } catch (DataAccessException e) {
      log.error(COULD_NOT_ACCESS_DATABASE, e);
      throw new InternalErrorException(COULD_NOT_ACCESS_DATABASE);
    }
  }


  @Override
  public void deleteMap(UUID id) {
    log.info("Deleting map: {}.");

    try {
      Optional<MapEntity> optional = repository.findById(id);

      if (optional.isEmpty()) {
        log.info(MAP_NOT_FOUND);
        throw new NotFoundException(MAP_NOT_FOUND);
      }

      repository.deleteById(id);

    } catch (DataAccessException e) {
      log.error(COULD_NOT_ACCESS_DATABASE, e);
      throw new InternalErrorException(COULD_NOT_ACCESS_DATABASE);
    }
  }

}
