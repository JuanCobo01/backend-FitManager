package com.uceva.fitmanager.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactInfoDTO {
    private String email;
    private String telefono;
    private String chatUrl;
    private String websiteUrl;
    private String direccion;
    private String horarioAtencion;
}
