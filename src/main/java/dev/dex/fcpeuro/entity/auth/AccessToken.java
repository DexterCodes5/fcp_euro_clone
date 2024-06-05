package dev.dex.fcpeuro.entity.auth;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccessToken {
    @Id
    @SequenceGenerator(name = "access_token_seq", sequenceName = "access_token_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "access_token_seq")
    private Integer id;
    private Integer userId;
    private String accessToken;
    private Boolean revoked;
    private Boolean expired;
}
