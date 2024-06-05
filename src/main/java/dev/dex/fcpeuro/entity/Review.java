package dev.dex.fcpeuro.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    @SequenceGenerator(name = "review_seq", sequenceName = "review_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "review_seq")
    private Integer id;
    @Positive
    private Integer partId;
    private Integer userId;
    private Instant createdAt;
    @NotBlank
    private String name;
    @Min(1)
    @Max(5)
    private Integer rating;
    @NotBlank
    @Size(min = 5)
    private String title;
    @NotBlank
    @Size(min = 10)
    private String text;
}
