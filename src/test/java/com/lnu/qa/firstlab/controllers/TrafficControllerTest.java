package com.lnu.qa.firstlab.controllers;

import com.lnu.qa.firstlab.models.Fruit;
import com.lnu.qa.firstlab.models.Tree;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.testng.MockitoTestNGListener;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.log4testng.Logger;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import static org.mockito.Mockito.when;

@Listeners(MockitoTestNGListener.class)
public class TrafficControllerTest {

    private final Logger log = Logger.getLogger(TrafficControllerTest.class);

    @Mock
    private FruitController fruitController;
    @Mock
    private TreeController treeController;

    @InjectMocks
    private HarvestController harvestController;


    private static List<Fruit> buildFruits(int count) {
        return IntStream.range(0, count).mapToObj(String::valueOf).map(Fruit::new).toList();
    }

    private static List<Tree> buildTrees(int count) {
        return IntStream.range(0, count).mapToObj(String::valueOf).map(Tree::new).toList();
    }

    private static double calculateExpectedHarvestIndex(int trees, int fruits) {
        return (double) Math.round(10000 * ((double) fruits / trees)) / 10000;
    }


    @BeforeClass
    public void setUp() {
        log.info("Current thread: %s".formatted(Thread.currentThread().getName()));
    }

    @AfterClass
    public void tearDown() {
        //NOP
    }

    @DataProvider(name = "harvest-data-provider")
    public Object[][] dpMethod() {
        return new Object[][]{
                {buildTrees(945), buildFruits(3), calculateExpectedHarvestIndex(3, 945)},
                {buildTrees(5474), buildFruits(23), calculateExpectedHarvestIndex(23, 5474)},
                {buildTrees(12), buildFruits(789), calculateExpectedHarvestIndex(789, 12)},
                {buildTrees(3), buildFruits(1), calculateExpectedHarvestIndex(1, 3)},
        };
    }

    @Test(dataProvider = "harvest-data-provider")
    public void shouldCalculateHarvestIndex(Object[] params) {
        //Given
        when(fruitController.retrieveAllFruits()).thenReturn((List<Fruit>) params[0]);
        when(treeController.retrieveAllTrees()).thenReturn((List<Tree>) params[1]);
        //Then
        Assert.assertEquals(harvestController.getHarvestIndex(), params[2]);
    }

    @Test
    public void shouldReturnZeroIfNoFruitsAvailable() {
        //Given
        when(fruitController.retrieveAllFruits()).thenReturn(Collections.emptyList());
        when(treeController.retrieveAllTrees()).thenReturn(buildTrees(1));
        //Then
        Assert.assertThrows(RuntimeException.class, () -> harvestController.getHarvestIndex());
    }

    @Test
    public void shouldReturnZeroIfNoTreesAvailable() {
        //Given
        when(fruitController.retrieveAllFruits()).thenReturn(buildFruits(1));
        when(treeController.retrieveAllTrees()).thenReturn(Collections.emptyList());
        //Then
        Assert.assertThrows(RuntimeException.class, () -> harvestController.getHarvestIndex());
    }

}