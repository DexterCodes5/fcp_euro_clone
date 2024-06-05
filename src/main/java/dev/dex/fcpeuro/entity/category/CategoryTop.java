package dev.dex.fcpeuro.entity.category;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryTop {
    @Id
    @GeneratedValue
    private Integer id;
    private String categoryTop;

    @OneToMany(mappedBy = "categoryTop")
    private List<CategoryMid> categoriesMid;
}
