package com.lnu.qa.firstlab.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@AllArgsConstructor
public class HarvestController {
    private FruitController fruitController;
    private TreeController treeController;


    private double getHarvestIndex() {
        return (double)fruitController.retrieveAllFruits().size() / treeController.retrieveAllTrees().size();
    }
}
