package dev.dex.fcpeuro.entity.vehicle.vehiclebodyengine;

import lombok.*;

import java.io.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleBodyEngineId implements Serializable {
    private Integer vehicleId;
    private Integer bodyId;
    private Integer engineId;
}
