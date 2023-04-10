package com.lnu.qa.secondlab.controllers;

import com.lnu.qa.firstlab.controllers.FruitController;
import com.lnu.qa.firstlab.models.Fruit;
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
public class FruitControllerTest {

    private static final String TEST_FRUIT_NAME = "test-fruit-orange";

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
//        fruitController.retrieveAllFruits().clear();
    }

    @Test(priority = 0)
    public void shouldRemoveEmptyListWhenNoFruitsAvailable() {
        Assert.assertEquals(fruitController.retrieveAllFruits().size(), 0);
    }

    @Test(priority = 1)
    public void shouldSaveFruit() {
        //Given
        var fruit = new Fruit(TEST_FRUIT_NAME);
        //When
        Fruit savedFruit = fruitController.saveFruit(fruit);
        //Then
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(savedFruit.getId().length() > 0);
        softAssert.assertEquals(savedFruit.getName(), TEST_FRUIT_NAME);
        var foundFruitsByName = fruitController.retrieveAllFruits().stream().filter(fr -> fr.getName().equals(TEST_FRUIT_NAME)).toList();
        softAssert.assertEquals(foundFruitsByName.size(), 1);
        softAssert.assertEquals(foundFruitsByName.get(0), savedFruit);
        softAssert.assertAll();
    }

    @Test(priority = 1)
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

    @Test(dependsOnMethods = "shouldSaveFruit", priority = 2)
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
        softAssert.assertEquals(allFruits.size(), 4);
        softAssert.assertTrue(Set.copyOf(allFruits).containsAll(Set.of(savedFruit_1, savedFruit_2)));
        softAssert.assertAll();
    }

    @Test(dependsOnMethods = "shouldSaveFruit", priority = 2)
    public void shouldGetFruitsByName() {
        //When
        List<Fruit> fruitsByName = fruitController.getFruitByName(TEST_FRUIT_NAME);
        //Then
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(fruitsByName.size() == 1);
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
    public void shouldThrowExceptionWhenAttemptToSaveFruitWithId() {
        //Given
        var fruit = new Fruit(RandomUtils.generateUUID());
        fruit.setId("id");
        //Then
        Assert.expectThrows(RuntimeException.class, () -> fruitController.saveFruit(fruit));
    }

    @Test
    public void shouldReturnNullIfFruitNotFoundById() {
        //When
        Fruit resultFruit = fruitController.getFruitById("not_valid_id");
        //Then
        Assert.assertNull(resultFruit);
    }

    @Test
    public void shouldReturnNullIfFruitNotFoundToRemoveById() {
        //When
        Fruit resultFruit = fruitController.removeFruitById("not_valid_id");
        //Then
        Assert.assertNull(resultFruit);
    }
}