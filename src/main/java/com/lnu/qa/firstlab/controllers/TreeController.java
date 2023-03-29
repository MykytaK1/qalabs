package com.lnu.qa.firstlab.controllers;

import com.lnu.qa.firstlab.models.Tree;
import com.lnu.qa.firstlab.utils.RandomUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class TreeController {
    private final List<Tree> trees = new ArrayList<>();

    public List<Tree> retrieveAllTrees() {
        return trees;
    }

    public Tree saveTree(Tree tree) {
        log.info("Attempt to save the Tree: %s".formatted(tree));
        if (tree.getId() != null) {
            throw new RuntimeException("Entity already exists with id: %s".formatted(tree.getId()));
        }
        tree.setId(RandomUtils.generateUUID());
        trees.add(tree);
        log.info("Tree is saved with id: %s".formatted(tree.getId()));
        return tree;
    }

    public Tree getTreeById(String id) {
        log.info("Attempt to take the Tree by id: %s".formatted(id));
        Tree resultTree = findTreeById(id);
        if (resultTree != null) {
            log.info("Tree was found: %s".formatted(resultTree));
        } else {
            log.info("Tree wasn't found");
        }
        return resultTree;
    }

    private Tree findTreeById(String id) {
        return trees.stream().filter(tree -> tree.getId().equals(id)).findFirst().orElse(null);
    }

    public Tree removeFruitById(String id) {
        log.info("Attempt to remove the Fruit by id: %s".formatted(id));
        Tree removedFruit = findTreeById(id);
        if (removedFruit != null) {
            trees.remove(removedFruit);
            log.info("Fruit was removed: {}", removedFruit);
        } else {
            log.info("Fruit wasn't found");
        }
        return removedFruit;
    }


}
