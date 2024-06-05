package dev.dex.fcpeuro.entity.vehicle.vehiclebodyengine;

import jakarta.persistence.*;
import lombok.*;

@Entity
@IdClass(VehicleBodyEngineId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleBodyEngine {
    @Id
    private Integer vehicleId;
    @Id
    private Integer bodyId;
    @Id
    private Integer engineId;
}
