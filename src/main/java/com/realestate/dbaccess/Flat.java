package com.realestate.dbaccess;

public class Flat {
    @Id
    private int id;
    private String district;
    private String street;
    private double space;
    private int roomsNumber;
    private double price;
    private String phoneNumber;

    public Flat() {
    }

    public Flat(int id, String district, String street, double space, int roomsNumber, double price, String phoneNumber) {
        this.id = id;
        this.district = district;
        this.street = street;
        this.space = space;
        this.roomsNumber = roomsNumber;
        this.price = price;
        this.phoneNumber = phoneNumber;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public double getSpace() {
        return space;
    }

    public void setSpace(double space) {
        this.space = space;
    }

    public int getRoomsNumber() {
        return roomsNumber;
    }

    public void setRoomsNumber(int roomsnumber) {
        this.roomsNumber = roomsnumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Flat{" +
                "id=" + id +
                ", district='" + district + '\'' +
                ", street='" + street + '\'' +
                ", space=" + space +
                ", roomsnumber=" + roomsNumber +
                ", price=" + price +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
