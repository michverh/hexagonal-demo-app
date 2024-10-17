package com.michel.hexagonaldemoapp.adapter.out.persistence.schema;

import com.michel.hexagonaldemoapp.domain.Address;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressValueObject {

    private String streetName;
    private String streetNumber;
    private String city;
    private String zipCode;
    private String country;

    public Address toDomain() {
        return new Address(streetName, streetNumber, city, zipCode, country);
    }

    public static AddressValueObject fromDomain(Address address) {
        if (address == null) return null;
        return new AddressValueObject(
                address.getStreetName(),
                address.getStreetNumber(),
                address.getCity(),
                address.getZipCode(),
                address.getCountry()
        );
    }
}

