package dev.dex.fcpeuro.entity.kitpart;

import dev.dex.fcpeuro.entity.*;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KitPart {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "kit_id")
    private Integer kitId;

    @ManyToOne
    @JoinColumn(name = "part_id")
    private Part part;

    private Integer quantity;
}
