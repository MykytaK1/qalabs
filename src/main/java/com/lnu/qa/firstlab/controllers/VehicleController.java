package com.lnu.qa.firstlab.controllers;

import com.lnu.qa.firstlab.models.Vehicle;
import com.lnu.qa.firstlab.utils.RandomUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class VehicleController {

    private final Logger logger = LogManager.getLogger(VehicleController.class.getName());
    private final List<Vehicle> vehicles = new ArrayList<>();

    public List<Vehicle> retrieveAllVehicles() {
        return vehicles;
    }

    public Vehicle saveVehicle(Vehicle vehicle) {
        log.info("Attempt to save the Vehicle: %s".formatted(vehicle));
        logger.info("Attempt to save the Vehicle: %s".formatted(vehicle));
        if (vehicle.getId() != null) {
            throw new RuntimeException("Entity already exists with id: %s".formatted(vehicle.getId()));
        }
        vehicle.setId(RandomUtils.generateUUID());
        vehicles.add(vehicle);
        logger.info("Vehicle is saved with id: %s".formatted(vehicle.getId()));
        return vehicle;
    }

    public Vehicle getVehicleById(String id) {
        logger.info("Attempt to take the Vehicle by id: %s".formatted(id));
        Vehicle resultVehicle = findVehicleById(id);
        if (resultVehicle != null) {
            logger.info("Vehicle was found: %s".formatted(resultVehicle));
        } else {
            logger.info("Vehicle wasn't found");
        }
        return resultVehicle;
    }


    private Vehicle findVehicleById(String id) {
        return vehicles.stream().filter(vehicle -> vehicle.getId().equals(id)).findFirst().orElse(null);
    }

    public Vehicle removeVehicleById(String id) {
        logger.info("Attempt to remove the Vehicle by id: %s".formatted(id));
        Vehicle removedVehicle = findVehicleById(id);
        if (removedVehicle != null) {
            vehicles.remove(removedVehicle);
            logger.info("Vehicle was removed: {}", removedVehicle);
        } else {
            logger.info("Vehicle wasn't found");
        }
        return removedVehicle;
    }
}
