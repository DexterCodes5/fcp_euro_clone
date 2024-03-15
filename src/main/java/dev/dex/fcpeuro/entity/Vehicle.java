package dev.dex.fcpeuro.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle {
    @Id
    @GeneratedValue
    private Integer id;
    private Integer yearFrom;
    private Integer yearTo;
    private String make;
    private String model;
    private String subModel;
    private String body;
    private String engine;
    private String transmission;
}
