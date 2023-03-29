package com.lnu.qa.firstlab.controllers;

import com.lnu.qa.firstlab.models.Road;
import com.lnu.qa.firstlab.utils.RandomUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;


public class RoadController {
    private final Logger log = LogManager.getLogger(RoadController.class.getName());
    private final List<Road> roads = new ArrayList<>();

    public List<Road> retrieveAllRoads() {
        return roads;
    }

    public Road saveRoad(Road Road) {
        log.info("Attempt to save the Road: %s".formatted(Road));
        if (Road.getId() != null) {
            throw new RuntimeException("Entity already exists with id: %s".formatted(Road.getId()));
        }
        Road.setId(RandomUtils.generateUUID());
        roads.add(Road);
        log.info("Road is saved with id: %s".formatted(Road.getId()));
        return Road;
    }

    public Road getRoadById(String id) {
        log.info("Attempt to take the Road by id: %s".formatted(id));
        Road resultRoad = roads.stream().filter(Road -> Road.getId().equals(id)).findFirst().orElse(null);
        if (resultRoad != null) {
            log.info("Road was found: %s".formatted(resultRoad));
        } else {
            log.info("Road wasn't found");
        }
        return resultRoad;
    }


    private Road findRoadById(String id) {
        return roads.stream().filter(road -> road.getId().equals(id)).findFirst().orElse(null);
    }

    public Road removeRoadById(String id) {
        log.info("Attempt to remove the Road by id: %s".formatted(id));
        Road removedRoad = findRoadById(id);
        if (removedRoad != null) {
            roads.remove(removedRoad);
            log.info("Road was removed: {}", removedRoad);
        } else {
            log.info("Road wasn't found");
        }
        return removedRoad;
    }
}
