package dev.dex.fcpeuro.entity.auth.resetpasswordtoken;

import jakarta.persistence.*;
import lombok.*;

import java.time.*;

@Entity
@IdClass(ResetPasswordTokenId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordToken {
    @Id
    private String token;
    @Id
    private Integer userId;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private Boolean active;
}
