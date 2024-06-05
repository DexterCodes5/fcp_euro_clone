package dev.dex.fcpeuro.model;

import lombok.*;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {
    private String categoryTop;
    private List<CategoryMidResponse> categoriesMid;
}
