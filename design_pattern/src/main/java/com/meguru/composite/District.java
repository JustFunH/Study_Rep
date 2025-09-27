package com.meguru.composite;

public class District implements PopulationNode {

    private final String name;

    private int population;

    public District(String name) {
        this.name = name;
    }

    @Override
    public int computePopulation() {
        return population;
    }
}
