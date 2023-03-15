package com.lnu.qa.firstlab.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@AllArgsConstructor
public class WealthController {
    private HouseController houseController;
    private VehicleController vehicleController;


    public double getCarsPerHouse() {
        return (double) vehicleController.retrieveAllCars().size() / houseController.retrieveAllHouses().size();
    }
}
