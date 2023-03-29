package com.lnu.qa.firstlab.controllers;

import com.lnu.qa.firstlab.models.Road;
import com.lnu.qa.firstlab.models.Vehicle;
import lombok.extern.slf4j.Slf4j;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import static org.mockito.Mockito.when;

@Slf4j
public class TrafficControllerTest extends MockitoSetup {

    @Mock
    private RoadController roadController;
    @Mock
    private VehicleController vehicleController;

    @InjectMocks
    private TrafficController trafficController;


    private static List<Road> buildRoads(int count) {
        return IntStream.range(0, count).mapToObj(String::valueOf).map(Road::new).toList();
    }

    private static List<Vehicle> buildVehicles(int count) {
        return IntStream.range(0, count).mapToObj(String::valueOf).map(Vehicle::new).toList();
    }

    private static double calculateExpectedTrafficIndex(int vehicles, int roads) {
        return (double) Math.round(10000 * ((double) roads / vehicles)) / 10000;
    }


    @BeforeClass
    public void setUp() {
        log.info("Current thread: %s".formatted(Thread.currentThread().getName()));
    }

    @AfterClass
    public void tearDown() {
        //NOP
    }

    @DataProvider(name = "traffic-data-provider")
    public Object[][] dpMethod() {
        return new Object[][]{
                {buildVehicles(945), buildRoads(3), calculateExpectedTrafficIndex(3, 945)},
                {buildVehicles(5474), buildRoads(23), calculateExpectedTrafficIndex(23, 5474)},
                {buildVehicles(12), buildRoads(789), calculateExpectedTrafficIndex(789, 12)},
                {buildVehicles(3), buildRoads(1), calculateExpectedTrafficIndex(1, 3)},
        };
    }

    @Test(dataProvider = "traffic-data-provider")
    public void shouldCalculateHarvestIndex(Object[] params) {
        //Given
        when(vehicleController.retrieveAllVehicles()).thenReturn((List<Vehicle>) params[0]);
        when(roadController.retrieveAllRoads()).thenReturn((List<Road>) params[1]);
        //Then
        Assert.assertEquals(trafficController.getTrafficIndex(), params[2]);
    }

    @Test
    public void shouldReturnZeroIfNoRoadsAvailable() {
        //Given
        when(roadController.retrieveAllRoads()).thenReturn(Collections.emptyList());
        when(vehicleController.retrieveAllVehicles()).thenReturn(buildVehicles(1));
        //Then
        Assert.assertThrows(RuntimeException.class, () -> trafficController.getTrafficIndex());
    }

    @Test
    public void shouldReturnZeroIfNoVehiclesAvailable() {
        //Given
        when(roadController.retrieveAllRoads()).thenReturn(buildRoads(1));
        when(vehicleController.retrieveAllVehicles()).thenReturn(Collections.emptyList());
        //Then
        Assert.assertThrows(RuntimeException.class, () -> trafficController.getTrafficIndex());
    }

}