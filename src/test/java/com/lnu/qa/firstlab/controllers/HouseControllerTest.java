package com.lnu.qa.firstlab.controllers;

import com.lnu.qa.firstlab.models.House;
import com.lnu.qa.firstlab.utils.RandomUtils;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;
import java.util.Set;

@Slf4j
public class HouseControllerTest {

    private HouseController houseController;

    @BeforeClass
    public void setUp() {
        log.info("Current thread: %s".formatted(Thread.currentThread().getName()));
        houseController = new HouseController();
    }

    @AfterClass
    public void tearDown() {
        houseController.retrieveAllHouses().clear();
    }

    @BeforeMethod
    public void afterTest() {
        houseController.retrieveAllHouses().clear();
    }

    @Test
    public void shouldSaveHouse() {
        //Given
        String name = RandomUtils.generateUUID();
        var house = new House(name);
        //When
        House savedHouse = houseController.saveHouse(house);
        //Then
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(savedHouse.getId().length() > 0);
        softAssert.assertEquals(savedHouse.getName(), name);
        var foundHousesByName = houseController.retrieveAllHouses().stream().filter(fr -> fr.getName().equals(name)).toList();
        softAssert.assertEquals(foundHousesByName.size(), 1);
        softAssert.assertEquals(foundHousesByName.get(0), savedHouse);
        softAssert.assertAll();
    }


    @Test
    public void shouldRemoveEmptyListWhenNoHousesAvailable() {
        Assert.assertEquals(houseController.retrieveAllHouses().size(), 0);
    }

    @Test
    public void shouldRetrieveAllHouses() {
        //Given
        String name_1 = RandomUtils.generateUUID();
        String name_2 = RandomUtils.generateUUID();
        //When
        House savedHouse_1 = houseController.saveHouse(new House(name_1));
        House savedHouse_2 = houseController.saveHouse(new House(name_2));
        //Then
        List<House> allHouses = houseController.retrieveAllHouses();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(allHouses.size(), 2);
        softAssert.assertEquals(Set.copyOf(allHouses), Set.of(savedHouse_1, savedHouse_2));
        softAssert.assertAll();
    }

    @Test
    public void shouldThrowExceptionWhenAttemptToSaveHouseWithId() {
        //Given
        var house = new House(RandomUtils.generateUUID());
        house.setId("id");
        //Then
        Assert.expectThrows(RuntimeException.class, () -> houseController.saveHouse(house));
    }

    @Test
    public void shouldGetHouseById() {
        //Given
        String name = RandomUtils.generateUUID();
        var house = new House(name);
        House savedHouse = houseController.saveHouse(house);
        //When
        House houseById = houseController.getHouseById(savedHouse.getId());
        //Then
        Assert.assertEquals(houseById, savedHouse);
    }

    @Test
    public void shouldReturnNullIfHouseNotFoundById() {
        //When
        House resultHouse = houseController.getHouseById("not_valid_id");
        //Then
        Assert.assertNull(resultHouse);
    }

    @Test
    public void shouldRemoveHouseById() {
        //Given
        String name = RandomUtils.generateUUID();
        var house = new House(name);
        House savedHouse = houseController.saveHouse(house);
        //When
        House houseById = houseController.removeHouseById(savedHouse.getId());
        //Then
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(houseById, savedHouse);
        var foundHousesByName = houseController.retrieveAllHouses().stream().filter(fr -> fr.getName().equals(name)).findFirst();
        softAssert.assertTrue(foundHousesByName.isEmpty());
        softAssert.assertAll();
    }

    @Test
    public void shouldReturnNullIfHouseNotFoundToRemoveById() {
        //When
        House resultHouse = houseController.removeHouseById("not_valid_id");
        //Then
        Assert.assertNull(resultHouse);
    }
}