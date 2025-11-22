package com.uceva.fitmanager.controller;

import com.uceva.fitmanager.model.dto.AppInfoDTO;
import com.uceva.fitmanager.model.dto.ContactInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/configuracion")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ConfiguracionController {

    @Value("${app.version:1.0.0}")
    private String appVersion;

    @Value("${app.build-number:1}")
    private String buildNumber;

    /**
     * GET /configuracion/app-info
     * Obtener información de la aplicación para AboutPage
     */
    @GetMapping("/app-info")
    public ResponseEntity<?> getAppInfo() {
        AppInfoDTO appInfo = AppInfoDTO.builder()
                .version(appVersion)
                .buildNumber(buildNumber)
                .appName("FitManager")
                .privacyPolicyUrl("https://fitmanager.com/privacidad")
                .termsUrl("https://fitmanager.com/terminos")
                .licenseUrl("https://fitmanager.com/licencia")
                .supportEmail("soporte@fitmanager.com")
                .build();

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", appInfo);
        response.put("message", "Información de la aplicación obtenida exitosamente");

        return ResponseEntity.ok(response);
    }

    /**
     * GET /configuracion/contacto
     * Obtener información de contacto para HelpSupportPage
     */
    @GetMapping("/contacto")
    public ResponseEntity<?> getContactInfo() {
        ContactInfoDTO contactInfo = ContactInfoDTO.builder()
                .email("soporte@fitmanager.com")
                .telefono("+57 300 123 4567")
                .chatUrl("https://tawk.to/fitmanager")
                .websiteUrl("https://fitmanager.com/ayuda")
                .direccion("Calle 123 #45-67, Tuluá, Valle del Cauca, Colombia")
                .horarioAtencion("Lunes a Viernes: 8:00 AM - 6:00 PM\nSábados: 9:00 AM - 1:00 PM")
                .build();

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", contactInfo);
        response.put("message", "Información de contacto obtenida exitosamente");

        return ResponseEntity.ok(response);
    }

    /**
     * GET /configuracion/idiomas
     * Obtener idiomas disponibles en la aplicación
     */
    @GetMapping("/idiomas")
    public ResponseEntity<?> getAvailableLanguages() {
        Map<String, String> languages = new HashMap<>();
        languages.put("es", "Español");
        languages.put("en", "English");
        languages.put("pt", "Português");

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", languages);
        response.put("message", "Idiomas disponibles obtenidos exitosamente");

        return ResponseEntity.ok(response);
    }
}
