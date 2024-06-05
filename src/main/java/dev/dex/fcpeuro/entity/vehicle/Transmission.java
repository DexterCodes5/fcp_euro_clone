package dev.dex.fcpeuro.entity.vehicle;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transmission {
    @Id
    @GeneratedValue
    private Integer id;
    private String transmission;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TransmissionNotSure {
        private String id;
        private String transmission;
    }
}
