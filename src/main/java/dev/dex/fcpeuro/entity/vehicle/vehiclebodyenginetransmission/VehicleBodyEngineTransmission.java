package dev.dex.fcpeuro.entity.vehicle.vehiclebodyenginetransmission;

import jakarta.persistence.*;
import lombok.*;

@Entity
@IdClass(VehicleBodyEngineTransmissionId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleBodyEngineTransmission {
    @Id
    private Integer vehicleId;
    @Id
    private Integer bodyId;
    @Id
    private Integer engineId;
    @Id
    private Integer transmissionId;
}
