package dev.dex.fcpeuro.entity.customerorder;

import jakarta.persistence.*;
import lombok.*;

import java.time.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Boolean receiveTextNotification;
    private Double price;
    private LocalDate receiveDate;
    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;
}
