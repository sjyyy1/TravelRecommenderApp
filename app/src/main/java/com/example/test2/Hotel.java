package com.example.test2;

public class Hotel {
    public String name;
    public int type;  // 0 for Hotel, 1 for Hostel
    public int zone;
    public int priceLevel;  // 0 for under 300, 1 for 300-800, 2 for over 800

    // Constructor
    public Hotel(String name, int type, int zone, int priceLevel) {
        this.name = name;
        this.type = type;
        this.zone = zone;
        this.priceLevel = priceLevel;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getZone() {
        return zone;
    }

    public void setZone(int zone) {
        this.zone = zone;
    }

    public int getPriceLevel() {
        return priceLevel;
    }

    public void setPriceLevel(int priceLevel) {
        this.priceLevel = priceLevel;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", zone=" + zone +
                ", priceLevel=" + priceLevel +
                '}';
    }
}
