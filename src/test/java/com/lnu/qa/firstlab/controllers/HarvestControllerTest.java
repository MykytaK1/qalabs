package com.lnu.qa.firstlab.controllers;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class HarvestControllerTest {

    private HarvestController harvestController;
    private FruitController fruitController;
    private TreeController treeController;

    @BeforeClass
    public void setUp() {
        fruitController = new FruitController();
        treeController = new TreeController();
        harvestController = new HarvestController(fruitController, treeController);
    }

    @AfterClass
    public void tearDown() {
    }

    @Test
    public void testGetHarvestIndex() {
        harvestController.getHarvestIndex();
    }
}