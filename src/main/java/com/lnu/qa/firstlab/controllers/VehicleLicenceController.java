package com.lnu.qa.firstlab.controllers;


import com.lnu.qa.firstlab.models.Car;
import com.lnu.qa.firstlab.models.Licence;
import lombok.AllArgsConstructor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@AllArgsConstructor
public class VehicleLicenceController {
    private final Logger log = LogManager.getLogger(VehicleLicenceController.class.getName());

    private VehicleController vehicleController;
    private LicenceController licenceController;

    public Licence activateLicenceForCar(String carId, String licenceId) {
        log.info("Activate licence: %s fro car: %s".formatted(licenceId, carId));
        Car carById = vehicleController.getCarById(carId);
        Licence licenceById = licenceController.getLicenceById(licenceId);
        if (carId != null && licenceById != null) {
            licenceById.getCars().add(carById);
        }
        return licenceById;
    }

}
