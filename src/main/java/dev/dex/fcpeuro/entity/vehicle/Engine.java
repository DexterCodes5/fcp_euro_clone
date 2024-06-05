package dev.dex.fcpeuro.entity.vehicle;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Engine {
    @Id
    @GeneratedValue
    private Integer id;
    private String engine;
}
