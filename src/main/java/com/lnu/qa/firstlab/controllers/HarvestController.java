package com.lnu.qa.firstlab.controllers;

import lombok.RequiredArgsConstructor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@RequiredArgsConstructor
public class HarvestController {
    private final Logger log = LogManager.getLogger(HarvestController.class.getName());
    private final FruitController fruitController;
    private final TreeController treeController;


    public double getHarvestIndex() {
        log.info("eeee");
        return (double) fruitController.retrieveAllFruits().size() / treeController.retrieveAllTrees().size();
    }
}
