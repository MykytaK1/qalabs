package com.lnu.qa.firstlab.controllers;

import com.lnu.qa.firstlab.models.House;

import java.util.ArrayList;
import java.util.List;
import com.lnu.qa.firstlab.utils.RandomUtils;

import com.lnu.qa.firstlab.utils.RandomUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class HouseController {
    private final Logger log = LogManager.getLogger(HouseController.class.getName());
    private final List<House> Houses = new ArrayList<>();

    public List<House> retrieveAllHouses() {
        return Houses;
    }

    public House saveHouse(House House) {
        log.info("Attempt to save the House: %s".formatted(House));
        if (House.getId() != null) {
            throw new RuntimeException("Entity already exists with id: %s".formatted(House.getId()));
        }
        House.setId(RandomUtils.generateUUID());
        Houses.add(House);
        log.info("House is saved with id: %s".formatted(House.getId()));
        return House;
    }

    public House getHouseById(String id) {
        log.info("Attempt to take the House by id: %s".formatted(id));
        House resultHouse = Houses.stream().filter(House -> House.getId().equals(id)).findFirst().orElse(null);
        if (resultHouse != null) {
            log.info("House was found: %s".formatted(resultHouse));
        } else {
            log.info("House wasn't found");
        }
        return resultHouse;
    }


}
