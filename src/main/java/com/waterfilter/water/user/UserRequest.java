package com.waterfilter.water.user;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    private String username;

    @NotBlank
    private String email;

    @NotBlank
    private String password;
    private String phoneNumber;
}
