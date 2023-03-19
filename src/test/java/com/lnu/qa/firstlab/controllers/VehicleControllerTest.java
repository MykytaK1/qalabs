package com.lnu.qa.firstlab.controllers;

import com.lnu.qa.firstlab.models.Vehicle;
import com.lnu.qa.firstlab.utils.RandomUtils;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.testng.log4testng.Logger;

import java.util.List;
import java.util.Set;

public class VehicleControllerTest {

    private final Logger log = Logger.getLogger(VehicleControllerTest.class);

    private VehicleController vehicleController;

    @BeforeClass
    public void setUp() {
        log.info("Current thread: %s".formatted(Thread.currentThread().getName()));
        vehicleController = new VehicleController();
    }

    @AfterClass
    public void tearDown() {
        vehicleController.retrieveAllVehicles().clear();
    }

    @BeforeMethod
    public void afterTest() {
        vehicleController.retrieveAllVehicles().clear();
    }

    @Test
    public void shouldSaveVehicle() {
        //Given
        String name = RandomUtils.generateUUID();
        var vehicle = new Vehicle(name);
        //When
        Vehicle savedVehicle = vehicleController.saveVehicle(vehicle);
        //Then
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(savedVehicle.getId().length() > 0);
        softAssert.assertEquals(savedVehicle.getName(), name);
        var foundVehiclesByName = vehicleController.retrieveAllVehicles().stream().filter(fr -> fr.getName().equals(name)).toList();
        softAssert.assertEquals(foundVehiclesByName.size(), 1);
        softAssert.assertEquals(foundVehiclesByName.get(0), savedVehicle);
        softAssert.assertAll();
    }


    @Test
    public void shouldRemoveEmptyListWhenNoVehiclesAvailable() {
        Assert.assertEquals(vehicleController.retrieveAllVehicles().size(), 0);
    }

    @Test
    public void shouldRetrieveAllVehicles() {
        //Given
        String name_1 = RandomUtils.generateUUID();
        String name_2 = RandomUtils.generateUUID();
        //When
        Vehicle savedVehicle_1 = vehicleController.saveVehicle(new Vehicle(name_1));
        Vehicle savedVehicle_2 = vehicleController.saveVehicle(new Vehicle(name_2));
        //Then
        List<Vehicle> allVehicles = vehicleController.retrieveAllVehicles();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(allVehicles.size(), 2);
        softAssert.assertEquals(Set.copyOf(allVehicles), Set.of(savedVehicle_1, savedVehicle_2));
        softAssert.assertAll();
    }

    @Test
    public void shouldThrowExceptionWhenAttemptToSaveVehicleWithId() {
        //Given
        var vehicle = new Vehicle(RandomUtils.generateUUID());
        vehicle.setId("id");
        //Then
        Assert.expectThrows(RuntimeException.class, () -> vehicleController.saveVehicle(vehicle));
    }

    @Test
    public void shouldGetVehicleById() {
        //Given
        String name = RandomUtils.generateUUID();
        var vehicle = new Vehicle(name);
        Vehicle savedVehicle = vehicleController.saveVehicle(vehicle);
        //When
        Vehicle vehicleById = vehicleController.getVehicleById(savedVehicle.getId());
        //Then
        Assert.assertEquals(vehicleById, savedVehicle);
    }

    @Test
    public void shouldReturnNullIfVehicleNotFoundById() {
        //When
        Vehicle resultVehicle = vehicleController.getVehicleById("not_valid_id");
        //Then
        Assert.assertNull(resultVehicle);
    }

    @Test
    public void shouldRemoveVehicleById() {
        //Given
        String name = RandomUtils.generateUUID();
        var vehicle = new Vehicle(name);
        Vehicle savedVehicle = vehicleController.saveVehicle(vehicle);
        //When
        Vehicle vehicleById = vehicleController.removeVehicleById(savedVehicle.getId());
        //Then
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(vehicleById, savedVehicle);
        var foundVehiclesByName = vehicleController.retrieveAllVehicles().stream().filter(fr -> fr.getName().equals(name)).findFirst();
        softAssert.assertTrue(foundVehiclesByName.isEmpty());
        softAssert.assertAll();
    }

    @Test
    public void shouldReturnNullIfVehicleNotFoundToRemoveById() {
        //When
        Vehicle resultVehicle = vehicleController.removeVehicleById("not_valid_id");
        //Then
        Assert.assertNull(resultVehicle);
    }
}