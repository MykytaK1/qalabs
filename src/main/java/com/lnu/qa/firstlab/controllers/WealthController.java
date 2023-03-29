package com.lnu.qa.firstlab.controllers;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@AllArgsConstructor
public class WealthController {
    private final Logger log = LogManager.getLogger(WealthController.class.getName());

    private HouseController houseController;
    private VehicleController vehicleController;


    public double getWealthIndex() {
        log.info("Calculate wealth index");
        int vehicles = vehicleController.retrieveAllVehicles().size();
        int houses = houseController.retrieveAllHouses().size();
        if (vehicles == 0 || houses == 0) {
            throw new RuntimeException("Not enough data to calculate the wealth index");
        }
        return (double) Math.round(10000 * ((double) vehicles / houses)) / 10000;
    }
}
