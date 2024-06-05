package dev.dex.fcpeuro.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private String firstName;
    private String lastName;
    private String streetAddress;
    private String streetAddressContd;
    private String companyName;
    private String city;
    private String country;
    private String state;
    private String zipCode;
    private String phoneNumber;
}
