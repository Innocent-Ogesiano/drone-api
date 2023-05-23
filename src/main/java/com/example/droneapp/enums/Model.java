package com.example.droneapp.enums;

public enum Model {
    LIGHT_WEIGHT(20.0),
    MIDDLE_WEIGHT(30.0),
    CRUISER_WEIGHT(40.0),
    HEAVY_WEIGHT(50.0);

    private final Double maxWeight;

    private Model (Double maxWeight) {
        this.maxWeight = maxWeight;
    }

    public Double getMaxWeight() {
        return this.maxWeight;
    }
}
