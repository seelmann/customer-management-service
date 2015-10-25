package com.example.cms.customerservice.domain;

import org.hibernate.validator.constraints.NotBlank;

public class Address {

    @NotBlank
    private String street;
    @NotBlank
    private String city;
    @NotBlank
    private String zipCode;
    @NotBlank
    private String country;

    Address() {
    }

    public Address(String street, String city, String zipCode, String country) {
        this.street = street;
        this.city = city;
        this.zipCode = zipCode;
        this.country = country;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getCountry() {
        return country;
    }

}
