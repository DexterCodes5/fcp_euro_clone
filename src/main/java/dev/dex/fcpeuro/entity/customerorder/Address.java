package dev.dex.fcpeuro.entity.customerorder;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstName;
    private String lastName;
    private String streetAddress;
    private String streetAddressContd;
    private String companyName;
    private String city;
    private String country;
    private String zipCode;
    private String phoneNumber;
}
