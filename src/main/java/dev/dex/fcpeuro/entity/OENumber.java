package dev.dex.fcpeuro.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "oe_number")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OENumber {
    @Id
    @GeneratedValue
    private Integer id;
    private String oeNumber;
}
