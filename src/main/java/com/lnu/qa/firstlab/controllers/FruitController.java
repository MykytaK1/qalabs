package com.lnu.qa.firstlab.controllers;

import com.lnu.qa.firstlab.models.Fruit;
import com.lnu.qa.firstlab.utils.RandomUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;


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
        fruit.setId(RandomUtils.generateUUID());
        fruits.add(fruit);
        log.info("Fruit is saved with id: %s".formatted(fruit.getId()));
        return fruit;
    }

    public Fruit getFruitById(String id) {
        log.info("Attempt to get the Fruit by id: {}", id);
        Fruit resultFruit = findFruitById(id);
        if (resultFruit != null) {
            log.info("Fruit was found: {}", resultFruit);
        } else {
            log.info("Fruit wasn't found");
        }
        return resultFruit;
    }

    private Fruit findFruitById(String id) {
        return fruits.stream().filter(fruit -> fruit.getId().equals(id)).findFirst().orElse(null);
    }

    public Fruit removeFruitById(String id) {
        log.info("Attempt to remove the Fruit by id: %s".formatted(id));
        Fruit removedFruit = findFruitById(id);
        if (removedFruit != null) {
            fruits.remove(removedFruit);
            log.info("Fruit was removed: {}", removedFruit);
        } else {
            log.info("Fruit wasn't found");
        }
        return removedFruit;
    }


}
