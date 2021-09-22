package com.example.cryptobank.domain;

public class Address {

    private String street;
    private String zipcode;
    private int houseNumber;
    private String addition;
    private String city;

    public Address(String street, String zipcode, int houseNumber, String addition, String city) {
        this.street = street;
        this.zipcode = zipcode;
        this.houseNumber = houseNumber;
        this.addition = addition;
        this.city = city;
    }

    public Address(String street, String zipcode, int houseNumber, String city) {
        this(street, zipcode, houseNumber, "", city);
    }

    public Address() {
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getzipcode() {
        return zipcode;
    }

    public void setzipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getAddition() {
        return addition;
    }

    public void setAddition(String addition) {
        this.addition = addition;
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                ", postalCode='" + zipcode + '\'' +
                ", houseNumber=" + houseNumber +
                ", addition='" + addition + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
