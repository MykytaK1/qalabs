package com.lnu.qa.firstlab.controllers;

import com.lnu.qa.firstlab.models.Vehicle;
import com.lnu.qa.firstlab.utils.RandomUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class VehicleController {

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
        Vehicle resultVehicle = findVehicleById(id);
        if (resultVehicle != null) {
            log.info("Vehicle was found: %s".formatted(resultVehicle));
        } else {
            log.info("Vehicle wasn't found");
        }
        return resultVehicle;
    }


    private Vehicle findVehicleById(String id) {
        return vehicles.stream().filter(vehicle -> vehicle.getId().equals(id)).findFirst().orElse(null);
    }

    public Vehicle removeVehicleById(String id) {
        log.info("Attempt to remove the Vehicle by id: %s".formatted(id));
        Vehicle removedVehicle = findVehicleById(id);
        if (removedVehicle != null) {
            vehicles.remove(removedVehicle);
            log.info("Vehicle was removed: {}", removedVehicle);
        } else {
            log.info("Vehicle wasn't found");
        }
        return removedVehicle;
    }
}
