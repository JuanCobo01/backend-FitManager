package com.uceva.fitmanager.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppleAuthDTO {
    
    @NotBlank(message = "El token de identidad de Apple es requerido")
    private String identityToken;
    
    @NotBlank(message = "El código de autorización es requerido")
    private String authorizationCode;
    
    // Información adicional del usuario (solo disponible en el primer login)
    private String name;
    private String email;
}
