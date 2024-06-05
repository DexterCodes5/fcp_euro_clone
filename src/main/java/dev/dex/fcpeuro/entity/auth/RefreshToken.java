package dev.dex.fcpeuro.entity.auth;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshToken {
    @Id
    @SequenceGenerator(name = "refresh_token_seq", sequenceName = "refresh_token_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "refresh_token_seq")
    private Integer id;
    private Integer userId;
    private String refreshToken;
    private Boolean revoked;
    private Boolean expired;
    private Boolean rememberMe;
}
