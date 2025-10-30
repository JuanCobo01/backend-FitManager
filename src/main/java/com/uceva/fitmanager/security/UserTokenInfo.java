package com.uceva.fitmanager.security;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserTokenInfo {
    private String email;
    private String userType; // usuario, entrenador, administrador
    private Long userId;
}
