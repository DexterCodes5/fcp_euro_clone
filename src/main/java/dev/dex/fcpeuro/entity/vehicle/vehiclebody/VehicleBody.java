package dev.dex.fcpeuro.entity.vehicle.vehiclebody;

import jakarta.persistence.*;
import lombok.*;

@Entity
@IdClass(VehicleBodyId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleBody {
    @Id
    private Integer vehicleId;
    @Id
    private Integer bodyId;
}
