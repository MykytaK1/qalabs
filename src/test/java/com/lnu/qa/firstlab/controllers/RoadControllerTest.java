package com.lnu.qa.firstlab.controllers;

import com.lnu.qa.firstlab.models.Road;
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
public class RoadControllerTest {

    private RoadController roadController;

    @BeforeClass
    public void setUp() {
        log.info("Current thread: %s".formatted(Thread.currentThread().getName()));
        roadController = new RoadController();
    }

    @AfterClass
    public void tearDown() {
        roadController.retrieveAllRoads().clear();
    }

    @BeforeMethod
    public void afterTest() {
        roadController.retrieveAllRoads().clear();
    }

    @Test
    public void shouldSaveRoad() {
        //Given
        String name = RandomUtils.generateUUID();
        var road = new Road(name);
        //When
        Road savedRoad = roadController.saveRoad(road);
        //Then
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(savedRoad.getId().length() > 0);
        softAssert.assertEquals(savedRoad.getName(), name);
        var foundRoadsByName = roadController.retrieveAllRoads().stream().filter(fr -> fr.getName().equals(name)).toList();
        softAssert.assertEquals(foundRoadsByName.size(), 1);
        softAssert.assertEquals(foundRoadsByName.get(0), savedRoad);
        softAssert.assertAll();
    }


    @Test
    public void shouldRemoveEmptyListWhenNoRoadsAvailable() {
        Assert.assertEquals(roadController.retrieveAllRoads().size(), 0);
    }

    @Test
    public void shouldRetrieveAllRoads() {
        //Given
        String name_1 = RandomUtils.generateUUID();
        String name_2 = RandomUtils.generateUUID();
        //When
        Road savedRoad_1 = roadController.saveRoad(new Road(name_1));
        Road savedRoad_2 = roadController.saveRoad(new Road(name_2));
        //Then
        List<Road> allRoads = roadController.retrieveAllRoads();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(allRoads.size(), 2);
        softAssert.assertEquals(Set.copyOf(allRoads), Set.of(savedRoad_1, savedRoad_2));
        softAssert.assertAll();
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void shouldThrowExceptionWhenAttemptToSaveRoadWithId() {
        //Given
        var road = new Road(RandomUtils.generateUUID());
        road.setId("id");
        //Then
        roadController.saveRoad(road);
    }

    @Test
    public void shouldGetRoadById() {
        //Given
        String name = RandomUtils.generateUUID();
        var road = new Road(name);
        Road savedRoad = roadController.saveRoad(road);
        //When
        Road roadById = roadController.getRoadById(savedRoad.getId());
        //Then
        Assert.assertEquals(roadById, savedRoad);
    }

    @Test
    public void shouldReturnNullIfRoadNotFoundById() {
        //When
        Road resultRoad = roadController.getRoadById("not_valid_id");
        //Then
        Assert.assertNull(resultRoad);
    }

    @Test
    public void shouldRemoveRoadById() {
        //Given
        String name = RandomUtils.generateUUID();
        var road = new Road(name);
        Road savedRoad = roadController.saveRoad(road);
        //When
        Road roadById = roadController.removeRoadById(savedRoad.getId());
        //Then
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(roadById, savedRoad);
        var foundRoadsByName = roadController.retrieveAllRoads().stream().filter(fr -> fr.getName().equals(name)).findFirst();
        softAssert.assertTrue(foundRoadsByName.isEmpty());
        softAssert.assertAll();
    }

    @Test
    public void shouldReturnNullIfRoadNotFoundToRemoveById() {
        //When
        Road resultRoad = roadController.removeRoadById("not_valid_id");
        //Then
        Assert.assertNull(resultRoad);
    }
}