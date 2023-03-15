package com.lnu.qa.firstlab.controllers;

import com.lnu.qa.firstlab.models.Fruit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class FruitController {
    private final Logger log = LogManager.getLogger(FruitController.class.getName());
    private final List<Fruit> fruits = new ArrayList<>();

    public List<Fruit> retrieveAllFruits() {
        return fruits;
    }

    public Fruit saveFruit(Fruit fruit) {
        log.info("Attempt to save the Fruit: %s".formatted(fruit));
        if (fruit.getId() != null) {
            throw new RuntimeException("Entity already exists with id: %s".formatted(fruit.getId()));
        }
        fruit.setId(UUID.randomUUID().toString());
        fruits.add(fruit);
        log.info("Fruit is saved with id: %s".formatted(fruit.getId()));
        return fruit;
    }

    public Fruit getFruitById(String id) {
        log.info("Attempt to take the Fruit by id: %s".formatted(id));
        Fruit resultFruit = fruits.stream().filter(fruit -> fruit.getId().equals(id)).findFirst().orElse(null);
        if (resultFruit != null) {
            log.info("Fruit was found: %s".formatted(resultFruit));
        } else {
            log.info("Fruit wasn't found");
        }
        return resultFruit;
    }

    public Fruit removeFruitById(String id) {
        log.info("Attempt to remove the Fruit by id: %s".formatted(id));
        Fruit resultFruit = fruits.stream().filter(fruit -> fruit.getId().equals(id)).findFirst().orElse(null);
        if (resultFruit != null) {
            fruits.remove(resultFruit);
            log.info("Fruit was removed: %s".formatted(resultFruit));
        } else {
            log.info("Fruit wasn't found");
        }
        return resultFruit;
    }


}
