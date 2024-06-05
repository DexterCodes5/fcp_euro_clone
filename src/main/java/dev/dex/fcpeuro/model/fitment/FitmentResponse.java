package dev.dex.fcpeuro.model.fitment;

import lombok.*;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FitmentResponse {
    private String make;
    private String model;
    private List<FitmentVehicle> vehicles;
}
