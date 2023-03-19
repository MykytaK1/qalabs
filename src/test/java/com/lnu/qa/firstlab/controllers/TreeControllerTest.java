package com.lnu.qa.firstlab.controllers;

import com.lnu.qa.firstlab.models.Tree;
import com.lnu.qa.firstlab.utils.RandomUtils;
import org.apache.logging.log4j.LogManager;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.testng.log4testng.Logger;

import java.util.List;
import java.util.Set;

public class TreeControllerTest {

    private final Logger log = Logger.getLogger(TreeControllerTest.class);

    private TreeController treeController;

    @BeforeClass
    public void setUp() {
        log.info("Current thread: %s".formatted(Thread.currentThread().getName()));
        treeController = new TreeController();
    }

    @AfterClass
    public void tearDown() {
        treeController.retrieveAllTrees().clear();
    }

    @BeforeMethod
    public void afterTest() {
        treeController.retrieveAllTrees().clear();
    }

    @Test
    public void shouldSaveTree() {
        //Given
        String name = RandomUtils.generateUUID();
        var tree = new Tree(name);
        //When
        Tree savedTree = treeController.saveTree(tree);
        //Then
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(savedTree.getId().length() > 0);
        softAssert.assertEquals(savedTree.getName(), name);
        var foundTreesByName = treeController.retrieveAllTrees().stream().filter(fr -> fr.getName().equals(name)).toList();
        softAssert.assertEquals(foundTreesByName.size(), 1);
        softAssert.assertEquals(foundTreesByName.get(0), savedTree);
        softAssert.assertAll();
    }


    @Test
    public void shouldRemoveEmptyListWhenNoTreesAvailable() {
        Assert.assertEquals(treeController.retrieveAllTrees().size(), 0);
    }

    @Test
    public void shouldRetrieveAllTrees() {
        //Given
        String name_1 = RandomUtils.generateUUID();
        String name_2 = RandomUtils.generateUUID();
        //When
        Tree savedTree_1 = treeController.saveTree(new Tree(name_1));
        Tree savedTree_2 = treeController.saveTree(new Tree(name_2));
        //Then
        List<Tree> allTrees = treeController.retrieveAllTrees();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(allTrees.size(), 2);
        softAssert.assertEquals(Set.copyOf(allTrees), Set.of(savedTree_1, savedTree_2));
        softAssert.assertAll();
    }

    @Test
    public void shouldThrowExceptionWhenAttemptToSaveTreeWithId() {
        //Given
        var tree = new Tree(RandomUtils.generateUUID());
        tree.setId("id");
        //Then
        Assert.expectThrows(RuntimeException.class, () -> treeController.saveTree(tree));
    }

    @Test
    public void shouldGetTreeById() {
        //Given
        String name = RandomUtils.generateUUID();
        var tree = new Tree(name);
        Tree savedTree = treeController.saveTree(tree);
        //When
        Tree treeById = treeController.getTreeById(savedTree.getId());
        //Then
        Assert.assertEquals(treeById, savedTree);
    }

    @Test
    public void shouldReturnNullIfTreeNotFoundById() {
        //When
        Tree resultTree = treeController.getTreeById("not_valid_id");
        //Then
        Assert.assertNull(resultTree);
    }

    @Test
    public void shouldRemoveTreeById() {
        //Given
        String name = RandomUtils.generateUUID();
        var tree = new Tree(name);
        Tree savedTree = treeController.saveTree(tree);
        //When
        Tree treeById = treeController.removeFruitById(savedTree.getId());
        //Then
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(treeById, savedTree);
        var foundTreesByName = treeController.retrieveAllTrees().stream().filter(fr -> fr.getName().equals(name)).findFirst();
        softAssert.assertTrue(foundTreesByName.isEmpty());
        softAssert.assertAll();
    }

    @Test
    public void shouldReturnNullIfTreeNotFoundToRemoveById() {
        //When
        Tree resultTree = treeController.removeFruitById("not_valid_id");
        //Then
        Assert.assertNull(resultTree);
    }
}