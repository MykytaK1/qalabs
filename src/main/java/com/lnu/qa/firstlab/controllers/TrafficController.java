package com.lnu.qa.firstlab.controllers;


import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@AllArgsConstructor
public class TrafficController {
    private final Logger log = LogManager.getLogger(TrafficController.class.getName());
    private RoadController roadController;
    private VehicleController vehicleController;


    public double getTrafficIndex() {
        log.info("Calculate traffic index");
        int vehicles = vehicleController.retrieveAllVehicles().size();
        int roads = roadController.retrieveAllRoads().size();
        if (vehicles == 0 || roads == 0) {
            throw new RuntimeException("Not enough data to calculate the traffic index");
        }
        return (double) Math.round(10000 * ((double) vehicles / roads)) / 10000;
    }

}
