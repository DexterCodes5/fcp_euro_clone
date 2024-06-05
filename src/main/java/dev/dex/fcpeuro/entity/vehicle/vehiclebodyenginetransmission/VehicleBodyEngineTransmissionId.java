package dev.dex.fcpeuro.entity.vehicle.vehiclebodyenginetransmission;

import lombok.*;

import java.io.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleBodyEngineTransmissionId implements Serializable {
    private Integer vehicleId;
    private Integer bodyId;
    private Integer engineId;
    private Integer transmissionId;
}
