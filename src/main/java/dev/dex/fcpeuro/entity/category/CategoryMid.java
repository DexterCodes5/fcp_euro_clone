package dev.dex.fcpeuro.entity.category;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryMid {
    @Id
    @GeneratedValue
    private Integer id;
    private String categoryMid;

    @ManyToOne
    @JoinColumn(name = "category_top_id")
    private CategoryTop categoryTop;

    @OneToMany(mappedBy = "categoryMid")
    private List<CategoryBot> categoriesBot;
}
