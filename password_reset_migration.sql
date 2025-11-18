-- Script de migración para sistema de recuperación de contraseñas
-- FitManager Backend - Noviembre 2025

-- Tabla para almacenar tokens de recuperación de contraseña
CREATE TABLE IF NOT EXISTS password_reset_tokens (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(100) NOT NULL,
    code VARCHAR(6) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    expires_at TIMESTAMP NOT NULL,
    used BOOLEAN DEFAULT FALSE,
    
    -- Índices para mejorar performance
    CONSTRAINT idx_email_code UNIQUE (email, code)
);

-- Índice para búsqueda por email y código
CREATE INDEX IF NOT EXISTS idx_password_reset_email_code ON password_reset_tokens(email, code) WHERE used = FALSE;

-- Índice para limpieza de tokens expirados
CREATE INDEX IF NOT EXISTS idx_password_reset_expires ON password_reset_tokens(expires_at);

-- Comentarios para documentación
COMMENT ON TABLE password_reset_tokens IS 'Tokens de recuperación de contraseña con expiración de 15 minutos';
COMMENT ON COLUMN password_reset_tokens.code IS 'Código de 6 dígitos enviado por email';
COMMENT ON COLUMN password_reset_tokens.expires_at IS 'Fecha y hora de expiración (15 minutos desde created_at)';
COMMENT ON COLUMN password_reset_tokens.used IS 'Indica si el token ya fue utilizado para restablecer contraseña';
