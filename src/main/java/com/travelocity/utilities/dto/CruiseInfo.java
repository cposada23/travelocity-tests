package com.travelocity.utilities.dto;

public class CruiseInfo {

    private String titleShip;
    private String price;

    public CruiseInfo(String titleShip, String price) {
        this.titleShip = titleShip;
        this.price = price;
    }

    public String getTitleShip() {
        return titleShip;
    }

    public String getPrice() {
        return price;
    }
}
