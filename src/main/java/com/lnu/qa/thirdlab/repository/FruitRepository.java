package com.lnu.qa.thirdlab.repository;

import com.lnu.qa.thirdlab.models.gen.Fruit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class FruitRepository {

    private final List<Fruit> fruits = new ArrayList<>();

    public Fruit saveFruit(Fruit fruit) {
        log.info("Attempt to save the Fruit: {}", fruit);
        if (fruit.getId() != null) {
            throw new RuntimeException("Entity already exists with id: " + fruit.getId());
        }
        fruit.setId(UUID.randomUUID().toString());
        fruits.add(fruit);
        log.info("Fruit is saved with id: {}", fruit.getId());
        return fruit;
    }

    public Fruit updateFruit(Fruit fruit) {
        log.info("Attempt to update the Fruit: {}", fruit);
        if (fruit.getId() == null) {
            throw new RuntimeException("Can't update the fruit without id");
        }
        removeFruitById(fruit.getId());
        fruits.add(fruit);
        log.info("Fruit is updated with id: {}", fruit.getId());
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
        log.info("Attempt to remove the Fruit by id: {}", id);
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
