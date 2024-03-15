package dev.dex.fcpeuro.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Part {
    @Id
    @GeneratedValue
    private Integer id;
    private String title;
    private String url;
    private String sku;
    private Integer quantity;
    private Double price;
    private String quality;
    private List<String> mfgNumbers;
    private Boolean kit;
    private String madeIn;
    private String productInformation;
    private String category1;
    private String category2;
    private String category3;
    private List<String> img;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToMany
    @JoinTable(name = "part_oe_number",
        joinColumns = @JoinColumn(name = "part_id"),
        inverseJoinColumns = @JoinColumn(name = "oe_number_id")
    )
    private List<OENumber> oeNumbers;

    @ManyToMany
    @JoinTable(name = "kit_part",
            joinColumns = @JoinColumn(name = "kit_id"),
            inverseJoinColumns = @JoinColumn(name = "part_id")
    )
    private List<Part> parts;

    @ManyToMany
    @JoinTable(name = "part_vehicle",
            joinColumns = @JoinColumn(name = "part_id"),
            inverseJoinColumns = @JoinColumn(name = "vehicle_id")
    )
    private List<Vehicle> vehicles;
}
