package com.lnu.qa.firstlab.controllers;


import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@AllArgsConstructor
public class TrafficController {
    private RoadController roadController;
    private VehicleController vehicleController;


    public double getCarsPerRoad() {
        return (double) vehicleController.retrieveAllCars().size() / roadController.retrieveAllRoads().size();
    }

}
