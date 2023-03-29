package com.lnu.qa.firstlab.controllers;


import com.lnu.qa.firstlab.models.License;
import com.lnu.qa.firstlab.models.Vehicle;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class VehicleLicenceController {

    private VehicleController vehicleController;
    private LicenceController licenceController;

    public License activateLicenceForVehicle(String vehicleId, String licenceId) {
        log.info("Activate licence: %s for vehicle: %s".formatted(licenceId, vehicleId));
        Vehicle vehicleById = vehicleController.getVehicleById(vehicleId);
        License licenseById = licenceController.getLicenceById(licenceId);
        if (vehicleById != null && licenseById != null) {
            licenseById.getVehicles().add(vehicleById);
        }
        return licenseById;
    }

}
