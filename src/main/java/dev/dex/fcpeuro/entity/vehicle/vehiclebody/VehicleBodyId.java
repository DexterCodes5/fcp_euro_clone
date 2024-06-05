package dev.dex.fcpeuro.entity.vehicle.vehiclebody;

import lombok.*;

import java.io.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleBodyId implements Serializable {
    private Integer vehicleId;
    private Integer bodyId;
}
