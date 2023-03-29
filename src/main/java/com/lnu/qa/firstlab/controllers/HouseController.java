package com.lnu.qa.firstlab.controllers;

import com.lnu.qa.firstlab.models.House;
import com.lnu.qa.firstlab.utils.RandomUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class HouseController {
    private final List<House> houses = new ArrayList<>();

    public List<House> retrieveAllHouses() {
        return houses;
    }

    public House saveHouse(House House) {
        log.info("Attempt to save the House: %s".formatted(House));
        if (House.getId() != null) {
            throw new RuntimeException("Entity already exists with id: %s".formatted(House.getId()));
        }
        House.setId(RandomUtils.generateUUID());
        houses.add(House);
        log.info("House is saved with id: %s".formatted(House.getId()));
        return House;
    }

    public House getHouseById(String id) {
        log.info("Attempt to take the House by id: %s".formatted(id));
        House resultHouse = findHouseById(id);
        if (resultHouse != null) {
            log.info("House was found: %s".formatted(resultHouse));
        } else {
            log.info("House wasn't found");
        }
        return resultHouse;
    }

    private House findHouseById(String id) {
        return houses.stream().filter(house -> house.getId().equals(id)).findFirst().orElse(null);
    }


    public House removeHouseById(String id) {
        log.info("Attempt to remove the House by id: %s".formatted(id));
        House removedHouse = findHouseById(id);
        if (removedHouse != null) {
            houses.remove(removedHouse);
            log.info("House was removed: {}", removedHouse);
        } else {
            log.info("House wasn't found");
        }
        return removedHouse;
    }
}
