package com.lnu.qa.firstlab.controllers;

import com.lnu.qa.firstlab.utils.MockitoSetup;
import com.lnu.qa.firstlab.models.License;
import com.lnu.qa.firstlab.models.Vehicle;
import com.lnu.qa.firstlab.utils.RandomUtils;
import lombok.extern.slf4j.Slf4j;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@Slf4j
public class VehicleLicenseControllerTest extends MockitoSetup {

    @Mock
    private VehicleController vehicleController;
    @Mock
    private LicenceController licenceController;

    @InjectMocks
    private VehicleLicenceController vehicleLicenceController;


    @BeforeClass
    public void setUp() {
        log.info("Current thread: %s".formatted(Thread.currentThread().getName()));
    }

    @AfterClass
    public void tearDown() {
        //NOP
    }

    @Test
    public void shouldActivateLicenceForVehicle() {
        //Given
        var licence = new License();
        String licenceId = RandomUtils.generateUUID();
        licence.setId(licenceId);
        when(licenceController.getLicenceById(eq(licenceId))).thenReturn(licence);

        var vehicle = new Vehicle(RandomUtils.generateUUID());
        String vehicleId = RandomUtils.generateUUID();
        vehicle.setId(vehicleId);
        when(vehicleController.getVehicleById(anyString())).thenReturn(vehicle);
        //When
        License resultLicense = vehicleLicenceController.activateLicenceForVehicle(vehicleId, licenceId);
        //Then
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(resultLicense.getId(), licenceId);
        softAssert.assertTrue(resultLicense.getVehicles().contains(vehicle));
        softAssert.assertAll();
    }

    @Test
    public void shouldNotActivateLicenceForVehicleWhenVehicleIdIsNotValid() {
        //Given
        var licence = new License();
        String licenceId = RandomUtils.generateUUID();
        licence.setId(licenceId);
        when(licenceController.getLicenceById(eq(licenceId))).thenReturn(licence);

        when(vehicleController.getVehicleById(anyString())).thenReturn(null);
        //When
        License resultLicense = vehicleLicenceController.activateLicenceForVehicle("not_valid", licenceId);
        //Then
        Assert.assertTrue(resultLicense.getVehicles().isEmpty());
    }

    @Test
    public void shouldNotActivateLicenceForVehicleWhenLicenceIdIsNotValid() {
        //Given
        when(licenceController.getLicenceById(anyString())).thenReturn(null);

        var vehicle = new Vehicle(RandomUtils.generateUUID());
        String vehicleId = RandomUtils.generateUUID();
        vehicle.setId(vehicleId);
        when(vehicleController.getVehicleById(anyString())).thenReturn(vehicle);
        //When
        License resultLicense = vehicleLicenceController.activateLicenceForVehicle("not_valid", vehicleId);
        //Then
        Assert.assertNull(resultLicense);
    }

}