package com.lnu.qa.firstlab.controllers;

import com.lnu.qa.firstlab.models.House;
import com.lnu.qa.firstlab.models.Vehicle;
import lombok.extern.slf4j.Slf4j;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.testng.MockitoTestNGListener;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import static org.mockito.Mockito.when;

@Slf4j
@Listeners(MockitoTestNGListener.class)
public class WealthControllerTest {

    @Mock
    private HouseController houseController;
    @Mock
    private VehicleController vehicleController;

    @InjectMocks
    private WealthController wealthController;


    private static List<House> buildHouses(int count) {
        return IntStream.range(0, count).mapToObj(String::valueOf).map(House::new).toList();
    }

    private static List<Vehicle> buildVehicles(int count) {
        return IntStream.range(0, count).mapToObj(String::valueOf).map(Vehicle::new).toList();
    }

    private static double calculateExpectedWealthIndex(int vehicles, int houses) {
        return (double) Math.round(10000 * ((double) vehicles / houses)) / 10000;
    }


    @BeforeClass
    public void setUp() {
        log.info("Current thread: %s".formatted(Thread.currentThread().getName()));
    }

    @AfterClass
    public void tearDown() {
        //NOP
    }

    @DataProvider(name = "wealth-data-provider")
    public Object[][] dpMethod() {
        return new Object[][]{
                {buildVehicles(945), buildHouses(3), calculateExpectedWealthIndex(3, 945)},
                {buildVehicles(5474), buildHouses(23), calculateExpectedWealthIndex(23, 5474)},
                {buildVehicles(12), buildHouses(789), calculateExpectedWealthIndex(789, 12)},
                {buildVehicles(3), buildHouses(1), calculateExpectedWealthIndex(1, 3)},
        };
    }

    @Test(dataProvider = "wealth-data-provider")
    public void shouldCalculateWealthIndex(Object[] params) {
        //Given
        when(houseController.retrieveAllHouses()).thenReturn((List<House>) params[0]);
        when(vehicleController.retrieveAllVehicles()).thenReturn((List<Vehicle>) params[1]);
        //Then
        Assert.assertEquals(wealthController.getWealthIndex(), params[2]);
    }

    @Test
    public void shouldReturnZeroIfNoHousesAvailable() {
        //Given
        when(houseController.retrieveAllHouses()).thenReturn(Collections.emptyList());
        when(vehicleController.retrieveAllVehicles()).thenReturn(buildVehicles(1));
        //Then
        Assert.assertThrows(RuntimeException.class, () -> wealthController.getWealthIndex());
    }

    @Test
    public void shouldReturnZeroIfNoVehiclesAvailable() {
        //Given
        when(houseController.retrieveAllHouses()).thenReturn(buildHouses(1));
        when(vehicleController.retrieveAllVehicles()).thenReturn(Collections.emptyList());
        //Then
        Assert.assertThrows(RuntimeException.class, () -> wealthController.getWealthIndex());
    }

}