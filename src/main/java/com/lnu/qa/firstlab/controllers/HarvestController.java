package com.lnu.qa.firstlab.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class HarvestController {
    private final FruitController fruitController;
    private final TreeController treeController;


    public double getHarvestIndex() {
        log.info("Calculate harvest index");
        int fruits = fruitController.retrieveAllFruits().size();
        int trees = treeController.retrieveAllTrees().size();
        if (fruits == 0 || trees == 0) {
            throw new RuntimeException("Not enough data to calculate the harvest index");
        }
        return (double) Math.round(10000 * ((double) fruits / trees)) / 10000;
    }
}
