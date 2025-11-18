package com.uceva.fitmanager.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoogleAuthDTO {
    
    @NotBlank(message = "El token de Google es requerido")
    private String idToken;
    
    // Información adicional opcional del usuario
    private String name;
    private String photoUrl;
}
