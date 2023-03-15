package com.lnu.qa.firstlab.controllers;


import lombok.AllArgsConstructor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@AllArgsConstructor
public class TrafficController {
    private final Logger log = LogManager.getLogger(TrafficController.class.getName());
    private RoadController roadController;
    private VehicleController vehicleController;


    public double getCarsPerRoad() {
        return (double) vehicleController.retrieveAllCars().size() / roadController.retrieveAllRoads().size();
    }

}
