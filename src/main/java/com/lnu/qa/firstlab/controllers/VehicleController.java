package com.lnu.qa.firstlab.controllers;

import com.lnu.qa.firstlab.models.Car;
import lombok.extern.log4j.Log4j;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Log4j
public class VehicleController {
    private final List<Car> cars = new ArrayList<>();

    public List<Car> retrieveAllCars() {
        return cars;
    }

    public Car saveCar(Car car) {
        log.info("Attempt to save the Car: %s".formatted(car));
        if (car.getId() != null) {
            throw new RuntimeException("Entity already exists with id: %s".formatted(car.getId()));
        }
        car.setId(UUID.randomUUID().toString());
        cars.add(car);
        log.info("Car is saved with id: %s".formatted(car.getId()));
        return car;
    }

    public Car getCarById(String id) {
        log.info("Attempt to take the Car by id: %s".formatted(id));
        Car resultCar = cars.stream().filter(car -> car.getId().equals(id)).findFirst().orElse(null);
        if (resultCar != null) {
            log.info("Car was found: %s".formatted(resultCar));
        } else {
            log.info("Car wasn't found");
        }
        return resultCar;
    }


}
