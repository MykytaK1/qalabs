package com.lnu.qa.firstlab.models;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class License {
    private String id;
    private Set<Vehicle> vehicles = new HashSet<>();
}
