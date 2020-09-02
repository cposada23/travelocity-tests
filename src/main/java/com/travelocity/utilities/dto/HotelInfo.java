package com.travelocity.utilities.dto;

public class HotelInfo {
    private String price;
    private String name;
    private double numberOfStars;

    public HotelInfo(String price, String name, double numberOfStars) {
        this.price = price;
        this.name = name;
        this.numberOfStars = numberOfStars;
    }

    public String getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public double getNumberOfStars() {
        return numberOfStars;
    }
}
