package com.meguru.composite;

import java.util.ArrayList;
import java.util.List;

public class City implements PopulationNode {

    private final String name;

    List<District> districtList = new ArrayList<>();

    public City(String name) {
        this.name = name;
    }

    public void addDistrict(District district) {
        this.districtList.add(district);
    }

    @Override
    public int computePopulation() {
        return districtList.stream()
                .mapToInt(PopulationNode::computePopulation).sum();
    }
}
