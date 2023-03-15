package com.lnu.qa.firstlab.controllers;

import com.lnu.qa.firstlab.models.Tree;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class TreeController {
    private final Logger log = LogManager.getLogger(TreeController.class.getName());
    private final List<Tree> Trees = new ArrayList<>();

    public List<Tree> retrieveAllTrees() {
        return Trees;
    }

    public Tree saveTree(Tree Tree) {
        log.info("Attempt to save the Tree: %s".formatted(Tree));
        if (Tree.getId() != null) {
            throw new RuntimeException("Entity already exists with id: %s".formatted(Tree.getId()));
        }
        Tree.setId(UUID.randomUUID().toString());
        Trees.add(Tree);
        log.info("Tree is saved with id: %s".formatted(Tree.getId()));
        return Tree;
    }

    public Tree getTreeById(String id) {
        log.info("Attempt to take the Tree by id: %s".formatted(id));
        Tree resultTree = Trees.stream().filter(Tree -> Tree.getId().equals(id)).findFirst().orElse(null);
        if (resultTree != null) {
            log.info("Tree was found: %s".formatted(resultTree));
        } else {
            log.info("Tree wasn't found");
        }
        return resultTree;
    }


}
