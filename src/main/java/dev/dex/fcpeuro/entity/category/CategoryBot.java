package dev.dex.fcpeuro.entity.category;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryBot {
    @Id
    @GeneratedValue
    private Integer id;
    private String categoryBot;

    @ManyToOne
    @JoinColumn(name = "category_mid_id")
    private CategoryMid categoryMid;
}
