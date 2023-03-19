package com.lnu.qa.firstlab.controllers;

import com.lnu.qa.firstlab.models.Vehicle;
import com.lnu.qa.firstlab.utils.RandomUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class VehicleController {

    private final Logger log = LogManager.getLogger(VehicleController.class.getName());
    private final List<Vehicle> vehicles = new ArrayList<>();

    public List<Vehicle> retrieveAllVehicles() {
        return vehicles;
    }

    public Vehicle saveVehicle(Vehicle vehicle) {
        log.info("Attempt to save the Vehicle: %s".formatted(vehicle));
        if (vehicle.getId() != null) {
            throw new RuntimeException("Entity already exists with id: %s".formatted(vehicle.getId()));
        }
        vehicle.setId(RandomUtils.generateUUID());
        vehicles.add(vehicle);
        log.info("Vehicle is saved with id: %s".formatted(vehicle.getId()));
        return vehicle;
    }

    public Vehicle getVehicleById(String id) {
        log.info("Attempt to take the Vehicle by id: %s".formatted(id));
        Vehicle resultVehicle = vehicles.stream().filter(vehicle -> vehicle.getId().equals(id)).findFirst().orElse(null);
        if (resultVehicle != null) {
            log.info("Vehicle was found: %s".formatted(resultVehicle));
        } else {
            log.info("Vehicle wasn't found");
        }
        return resultVehicle;
    }


}
