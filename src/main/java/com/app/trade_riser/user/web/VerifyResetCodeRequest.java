package com.app.trade_riser.user.web;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class VerifyResetCodeRequest {
    @NotBlank
    private String email;
    
    @NotBlank
    private String code;

    public VerifyResetCodeRequest(String email, String code) {
        this.email = email;
        this.code = code;
    }
}
