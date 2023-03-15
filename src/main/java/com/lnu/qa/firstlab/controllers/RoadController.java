package com.lnu.qa.firstlab.controllers;

import com.lnu.qa.firstlab.models.Road;
import lombok.extern.log4j.Log4j;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Log4j
public class RoadController {
    private final List<Road> Roads = new ArrayList<>();

    public List<Road> retrieveAllRoads() {
        return Roads;
    }

    public Road saveRoad(Road Road) {
        log.info("Attempt to save the Road: %s".formatted(Road));
        if (Road.getId() != null) {
            throw new RuntimeException("Entity already exists with id: %s".formatted(Road.getId()));
        }
        Road.setId(UUID.randomUUID().toString());
        Roads.add(Road);
        log.info("Road is saved with id: %s".formatted(Road.getId()));
        return Road;
    }

    public Road getRoadById(String id) {
        log.info("Attempt to take the Road by id: %s".formatted(id));
        Road resultRoad = Roads.stream().filter(Road -> Road.getId().equals(id)).findFirst().orElse(null);
        if (resultRoad != null) {
            log.info("Road was found: %s".formatted(resultRoad));
        } else {
            log.info("Road wasn't found");
        }
        return resultRoad;
    }


}
