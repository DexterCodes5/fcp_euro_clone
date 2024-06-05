package dev.dex.fcpeuro.entity.auth.resetpasswordtoken;

import lombok.*;

import java.io.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordTokenId implements Serializable {
    private String token;
    private Integer userId;
}
