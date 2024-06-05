package dev.dex.fcpeuro.model.fitment;

public record FitmentVehicle(
        Integer id,
        Integer year,
        String make,
        String model,
        String comment
) {
}
