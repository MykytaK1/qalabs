package com.lnu.qa.firstlab.controllers;

import lombok.AllArgsConstructor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@AllArgsConstructor
public class WealthController {
    private final Logger log = LogManager.getLogger(WealthController.class.getName());

    private HouseController houseController;
    private VehicleController vehicleController;


    public double getCarsPerHouse() {
        return (double) vehicleController.retrieveAllCars().size() / houseController.retrieveAllHouses().size();
    }
}
