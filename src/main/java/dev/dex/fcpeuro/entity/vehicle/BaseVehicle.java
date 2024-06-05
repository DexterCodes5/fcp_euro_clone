package dev.dex.fcpeuro.entity.vehicle;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseVehicle {
    @Id
    @GeneratedValue
    private Integer id;
    private Integer year;
    private Integer makeId;
    private String model;
}
