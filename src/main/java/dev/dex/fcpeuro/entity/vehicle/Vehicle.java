package dev.dex.fcpeuro.entity.vehicle;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {
    @Id
    @GeneratedValue
    private Integer id;
    private Integer baseVehicleId;
    private String subModel;
}
