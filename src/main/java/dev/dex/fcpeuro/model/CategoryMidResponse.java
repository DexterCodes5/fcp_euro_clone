package dev.dex.fcpeuro.model;

import lombok.*;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryMidResponse {
    private String categoryMid;
    private List<String> categoriesBot;
}
