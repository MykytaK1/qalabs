package com.lnu.qa.firstlab.controllers;

import com.lnu.qa.firstlab.models.Licence;
import com.lnu.qa.firstlab.models.Vehicle;
import com.lnu.qa.firstlab.utils.RandomUtils;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.testng.MockitoTestNGListener;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.testng.log4testng.Logger;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@Listeners(MockitoTestNGListener.class)
public class VehicleLicenceControllerTest {

    private final Logger log = Logger.getLogger(VehicleLicenceControllerTest.class);

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
        var licence = new Licence();
        String licenceId = RandomUtils.generateUUID();
        licence.setId(licenceId);
        when(licenceController.getLicenceById(eq(licenceId))).thenReturn(licence);

        var vehicle = new Vehicle(RandomUtils.generateUUID());
        String vehicleId = RandomUtils.generateUUID();
        vehicle.setId(vehicleId);
        when(vehicleController.getVehicleById(anyString())).thenReturn(vehicle);
        //When
        Licence resultLicence = vehicleLicenceController.activateLicenceForVehicle(vehicleId, licenceId);
        //Then
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(resultLicence.getId(), licenceId);
        softAssert.assertTrue(resultLicence.getVehicles().contains(vehicle));
        softAssert.assertAll();
    }

    @Test
    public void shouldNotActivateLicenceForVehicleWhenVehicleIdIsNotValid() {
        //Given
        var licence = new Licence();
        String licenceId = RandomUtils.generateUUID();
        licence.setId(licenceId);
        when(licenceController.getLicenceById(eq(licenceId))).thenReturn(licence);

        when(vehicleController.getVehicleById(anyString())).thenReturn(null);
        //When
        Licence resultLicence = vehicleLicenceController.activateLicenceForVehicle("not_valid", licenceId);
        //Then
        Assert.assertTrue(resultLicence.getVehicles().isEmpty());
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
        Licence resultLicence = vehicleLicenceController.activateLicenceForVehicle("not_valid", vehicleId);
        //Then
        Assert.assertNull(resultLicence);
    }

}