package com.egin.auth.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenInvalidateRequest {

    @NotBlank
    private String accessToken;

    @NotBlank
    private String refreshToken;


}
