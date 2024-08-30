package com.example.test2;

public class Attraction {
    public String name;       // 景点名称
    public int category;      // 景点分类：0, 1, 2, 3, 4
    public int zone;          // 景点片区：1-12

    // 构造函数
    public Attraction(String name, int category, int zone) {
        this.name = name;
        this.category = category;
        this.zone = zone;
    }

    // 获取景点名称
    public String getName() {
        return name;
    }

    // 获取景点分类
    public int getCategory() {
        return category;
    }

    // 获取景点片区
    public int getZone() {
        return zone;
    }

    @Override
    public String toString() {
        return name;
    }
}
