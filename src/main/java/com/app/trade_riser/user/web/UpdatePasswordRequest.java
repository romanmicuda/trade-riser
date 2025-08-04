package com.app.trade_riser.user.web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdatePasswordRequest {
    @NotBlank
    private String email;
    
    @NotBlank
    private String code;
    
    @NotBlank
    @Size(min = 6, max = 40)
    private String newPassword;

    public UpdatePasswordRequest(String email, String code, String newPassword) {
        this.email = email;
        this.code = code;
        this.newPassword = newPassword;
    }
}
