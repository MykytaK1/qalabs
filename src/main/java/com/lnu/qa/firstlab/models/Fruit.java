package com.lnu.qa.firstlab.models;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Fruit {
    private String id;
    private final String name;
}
