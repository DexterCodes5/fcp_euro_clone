package dev.dex.fcpeuro.entity;

import com.fasterxml.jackson.annotation.*;
import dev.dex.fcpeuro.entity.category.*;
import dev.dex.fcpeuro.entity.kitpart.*;
import dev.dex.fcpeuro.entity.vehicle.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

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
    private Integer fcpEuroId;
    private Integer quantity;
    private Double price;
    private String quality;
    private List<String> mfgNumbers;
    private Boolean kit;
    private String madeIn;
    private List<String> img;
    private Boolean universal;
    private String productInformationHtml;
    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToMany
    @JoinTable(name = "vehicle_part",
            joinColumns = @JoinColumn(name = "part_id"),
            inverseJoinColumns = @JoinColumn(name = "vehicle_id")
    )
    private List<Vehicle> vehicles;

    @ManyToMany
    @JoinTable(name = "part_oe_number",
        joinColumns = @JoinColumn(name = "part_id"),
        inverseJoinColumns = @JoinColumn(name = "oe_number_id")
    )
    private List<OENumber> oeNumbers;

    @ManyToOne
    @JoinColumn(name = "category_bot_id")
    @JsonIgnore
    private CategoryBot categoryBot;

    @OneToMany
    @JoinColumn(name = "kit_id")
    private List<KitPart> kitParts;
}
