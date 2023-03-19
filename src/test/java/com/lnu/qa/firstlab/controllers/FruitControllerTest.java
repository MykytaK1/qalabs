package com.lnu.qa.firstlab.controllers;

import com.lnu.qa.firstlab.models.Fruit;
import com.lnu.qa.firstlab.utils.RandomUtils;
import org.apache.logging.log4j.LogManager;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import org.testng.log4testng.Logger;

import java.util.List;
import java.util.Set;

public class FruitControllerTest {

    private final Logger log = Logger.getLogger(FruitControllerTest.class);

    private FruitController fruitController;

    @BeforeClass
    public void setUp() {
        log.info("Current thread: %s".formatted(Thread.currentThread().getName()));
        fruitController = new FruitController();
    }

    @AfterClass
    public void tearDown() {
        fruitController.retrieveAllFruits().clear();
    }

    @BeforeMethod
    public void afterTest() {
        fruitController.retrieveAllFruits().clear();
    }

    @Test
    public void shouldSaveFruit() {
        //Given
        String name = RandomUtils.generateUUID();
        var fruit = new Fruit(name);
        //When
        Fruit savedFruit = fruitController.saveFruit(fruit);
        //Then
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(savedFruit.getId().length() > 0);
        softAssert.assertEquals(savedFruit.getName(), name);
        var foundFruitsByName = fruitController.retrieveAllFruits().stream().filter(fr -> fr.getName().equals(name)).toList();
        softAssert.assertEquals(foundFruitsByName.size(), 1);
        softAssert.assertEquals(foundFruitsByName.get(0), savedFruit);
        softAssert.assertAll();
    }


    @Test
    public void shouldRemoveEmptyListWhenNoFruitsAvailable() {
        Assert.assertEquals(fruitController.retrieveAllFruits().size(), 0);
    }

    @Test
    public void shouldRetrieveAllFruits() {
        //Given
        String name_1 = RandomUtils.generateUUID();
        String name_2 = RandomUtils.generateUUID();
        //When
        Fruit savedFruit_1 = fruitController.saveFruit(new Fruit(name_1));
        Fruit savedFruit_2 = fruitController.saveFruit(new Fruit(name_2));
        //Then
        List<Fruit> allFruits = fruitController.retrieveAllFruits();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(allFruits.size(), 2);
        softAssert.assertEquals(Set.copyOf(allFruits), Set.of(savedFruit_1, savedFruit_2));
        softAssert.assertAll();
    }

    @Test
    public void shouldThrowExceptionWhenAttemptToSaveFruitWithId() {
        //Given
        var fruit = new Fruit(RandomUtils.generateUUID());
        fruit.setId("id");
        //Then
        Assert.expectThrows(RuntimeException.class, () -> fruitController.saveFruit(fruit));
    }

    @Test
    public void shouldGetFruitById() {
        //Given
        String name = RandomUtils.generateUUID();
        var fruit = new Fruit(name);
        Fruit savedFruit = fruitController.saveFruit(fruit);
        //When
        Fruit fruitById = fruitController.getFruitById(savedFruit.getId());
        //Then
        Assert.assertEquals(fruitById, savedFruit);
    }

    @Test
    public void shouldReturnNullIfFruitNotFoundById() {
        //When
        Fruit resultFruit = fruitController.getFruitById("not_valid_id");
        //Then
        Assert.assertNull(resultFruit);
    }

    @Test
    public void shouldRemoveFruitById() {
        //Given
        String name = RandomUtils.generateUUID();
        var fruit = new Fruit(name);
        Fruit savedFruit = fruitController.saveFruit(fruit);
        //When
        Fruit fruitById = fruitController.removeFruitById(savedFruit.getId());
        //Then
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(fruitById, savedFruit);
        var foundFruitsByName = fruitController.retrieveAllFruits().stream().filter(fr -> fr.getName().equals(name)).findFirst();
        softAssert.assertTrue(foundFruitsByName.isEmpty());
        softAssert.assertAll();
    }

    @Test
    public void shouldReturnNullIfFruitNotFoundToRemoveById() {
        //When
        Fruit resultFruit = fruitController.removeFruitById("not_valid_id");
        //Then
        Assert.assertNull(resultFruit);
    }
}