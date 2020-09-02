package com.travelocity.utilities.dto;

public class TripDetails {
    private String totalPrice;
    private String hotelName;
    private String tripDate;
    private String priceDueAtHotel;

    public TripDetails(String totalPrice, String hotelName, String tripDate, String priceDueAtHotel) {
        this.totalPrice = totalPrice;
        this.hotelName = hotelName;
        this.tripDate = tripDate;
        this.priceDueAtHotel = priceDueAtHotel;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public String getHotelName() {
        return hotelName;
    }

    public String getTripDate() {
        return tripDate;
    }

    public String getPriceDueAtHotel() {
        return priceDueAtHotel;
    }
}
