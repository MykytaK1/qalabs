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
public class WealthControllerTest {

    private final Logger log = Logger.getLogger(WealthControllerTest.class);

    @Mock
    private FruitController fruitController;
    @Mock
    private TreeController treeController;

    @InjectMocks
    private WealthController wealthController;


    private static List<Fruit> buildFruits(int count) {
        return IntStream.range(0, count).mapToObj(String::valueOf).map(Fruit::new).toList();
    }

    private static List<Tree> buildTrees(int count) {
        return IntStream.range(0, count).mapToObj(String::valueOf).map(Tree::new).toList();
    }

    private static double calculateExpectedWealthIndex(int trees, int fruits) {
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

    @DataProvider(name = "wealth-data-provider")
    public Object[][] dpMethod() {
        return new Object[][]{
                {buildTrees(945), buildFruits(3), calculateExpectedWealthIndex(3, 945)},
                {buildTrees(5474), buildFruits(23), calculateExpectedWealthIndex(23, 5474)},
                {buildTrees(12), buildFruits(789), calculateExpectedWealthIndex(789, 12)},
                {buildTrees(3), buildFruits(1), calculateExpectedWealthIndex(1, 3)},
        };
    }

    @Test(dataProvider = "wealth-data-provider")
    public void shouldCalculateWealthIndex(Object[] params) {
        //Given
        when(fruitController.retrieveAllFruits()).thenReturn((List<Fruit>) params[0]);
        when(treeController.retrieveAllTrees()).thenReturn((List<Tree>) params[1]);
        //Then
        Assert.assertEquals(wealthController.getWealthIndex(), params[2]);
    }

    @Test
    public void shouldReturnZeroIfNoFruitsAvailable() {
        //Given
        when(fruitController.retrieveAllFruits()).thenReturn(Collections.emptyList());
        when(treeController.retrieveAllTrees()).thenReturn(buildTrees(1));
        //Then
        Assert.assertThrows(RuntimeException.class, () -> wealthController.getWealthIndex());
    }

    @Test
    public void shouldReturnZeroIfNoTreesAvailable() {
        //Given
        when(fruitController.retrieveAllFruits()).thenReturn(buildFruits(1));
        when(treeController.retrieveAllTrees()).thenReturn(Collections.emptyList());
        //Then
        Assert.assertThrows(RuntimeException.class, () -> wealthController.getWealthIndex());
    }

}