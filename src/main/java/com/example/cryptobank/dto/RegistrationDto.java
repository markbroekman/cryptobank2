package com.example.cryptobank.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;


//        Customer customerToRegister = new Customer(customerRegistrationDto.getUsername(), customerRegistrationDto.getPassword(),
//                customerRegistrationDto.getFirstName(), customerRegistrationDto.getLastName(), customerRegistrationDto.getDateOfBirth(),
//                customerRegistrationDto.getSocialSecurityNumber(), customerRegistrationDto.getStreet(), customerRegistrationDto.getZipcode(),
//                customerRegistrationDto.getHousenumber(), customerRegistrationDto.getAddition(), customerRegistrationDto.getCity(),
//                customerRegistrationDto.getEmail());

public class RegistrationDto {
    private final Logger logger = LoggerFactory.getLogger(RegistrationDto.class);
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private int socialSecurityNumber;
    private String street;
    private String zipcode;
    private int housenumber;
    private String addition;
    private String city;
    private String email;

    public RegistrationDto(String username, String password, String firstName, String lastName, LocalDate dateOfBirth,
                           int socialSecurityNumber, String street, String zipcode, int housenumber, String addition,
                           String city, String email) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.socialSecurityNumber = socialSecurityNumber;
        this.street = street;
        this.zipcode = zipcode;
        this.housenumber = housenumber;
        this.addition = addition;
        this.city = city;
        this.email = email;
    }

    public RegistrationDto() {
        logger.info("New RegistrationDto");
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    public void setSocialSecurityNumber(int socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public int getHousenumber() {
        return housenumber;
    }

    public void setHousenumber(int housenumber) {
        this.housenumber = housenumber;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "RegistrationDto{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", socialSecurityNumber=" + socialSecurityNumber +
                ", street='" + street + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", housenumber=" + housenumber +
                ", addition='" + addition + '\'' +
                ", city='" + city + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}