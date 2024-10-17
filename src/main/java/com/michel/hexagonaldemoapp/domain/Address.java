package com.michel.hexagonaldemoapp.domain;

import lombok.Getter;
import lombok.Setter;

import static java.text.MessageFormat.format;

@Getter
public class Address {
    @Setter private String streetName;
    @Setter private String streetNumber;
    private String zipCode;
    @Setter private String city;
    @Setter private String country;

    public Address(final String streetName, final String streetNumber, final String zipCode, final String city,
                   final String country) {
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        setZipCode(zipCode);
        this.city = city;
        this.country = country;
    }

    public void setZipCode(String zipCode) {
        if(!zipCode.trim().toUpperCase().matches("^[0-9]{4}[A-Z]{2}")) {
            throw new IllegalArgumentException(format("Invalid postal code: " + zipCode));
        }
        this.zipCode = zipCode.trim();
    }
}
