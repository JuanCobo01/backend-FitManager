package com.uceva.fitmanager.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenCleanupService {

    private final JwtService jwtService;

    @Scheduled(fixedRateString = "${jwt.activity-check}")
    public void cleanupInactiveTokens() {
        log.info("Ejecutando limpieza de tokens inactivos...");
        // El JwtService ya maneja la limpieza automática cuando se valida un token
        // Este método podría expandirse para hacer limpieza más agresiva si es necesario
    }

    @Scheduled(cron = "0 0 2 * * ?") // Ejecutar a las 2 AM todos los días
    public void dailyTokenCleanup() {
        log.info("Ejecutando limpieza diaria de tokens...");
        // Limpieza más profunda si es necesaria
    }
}
