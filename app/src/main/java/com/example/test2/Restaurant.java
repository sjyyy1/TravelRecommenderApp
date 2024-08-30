package com.example.test2;

public class Restaurant {
    String name;
    String[] nearbyAttractions;
    String cuisineType;
    String priceRange;

    public Restaurant(String name, String[] nearbyAttractions, String cuisineType, String priceRange) {
        this.name = name;
        this.nearbyAttractions = nearbyAttractions;
        this.cuisineType = cuisineType;
        this.priceRange = priceRange;
    }

    @Override
    public String toString() {
        return name;
    }
}
