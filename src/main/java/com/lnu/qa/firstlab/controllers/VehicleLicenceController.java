package com.lnu.qa.firstlab.controllers;


import com.lnu.qa.firstlab.models.Licence;
import com.lnu.qa.firstlab.models.Vehicle;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@AllArgsConstructor
public class VehicleLicenceController {
    private final Logger log = LogManager.getLogger(VehicleLicenceController.class.getName());

    private VehicleController vehicleController;
    private LicenceController licenceController;

    public Licence activateLicenceForVehicle(String vehicleId, String licenceId) {
        log.info("Activate licence: %s for vehicle: %s".formatted(licenceId, vehicleId));
        Vehicle vehicleById = vehicleController.getVehicleById(vehicleId);
        Licence licenceById = licenceController.getLicenceById(licenceId);
        if (vehicleById != null && licenceById != null) {
            licenceById.getVehicles().add(vehicleById);
        }
        return licenceById;
    }

}
