package com.lnu.qa.firstlab.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class WealthController {

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
