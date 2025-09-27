package com.meguru.composite;

import java.util.ArrayList;
import java.util.List;

public class Province implements PopulationNode {

    private final String name;

    List<City> cityList = new ArrayList<>();

    public Province(String name) {
        this.name = name;
    }

    public void addCity(City city) {
        this.cityList.add(city);
    }

    @Override
    public int computePopulation() {
        return cityList.stream()
                .mapToInt(City::computePopulation).sum();
    }
}
