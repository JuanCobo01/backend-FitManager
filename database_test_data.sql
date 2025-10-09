-- =====================================================
-- SCRIPT DE DATOS DE PRUEBA PARA FITMANAGER
-- =====================================================
-- Este script contiene datos de prueba realistas para probar todos los endpoints
-- Ejecutar después de crear las tablas con JPA

-- Limpiar datos existentes (opcional)
-- DELETE FROM detalle_rutina;
-- DELETE FROM progresos;
-- DELETE FROM pagos;
-- DELETE FROM rutinas;
-- DELETE FROM ejercicios;
-- DELETE FROM entrenadores;
-- DELETE FROM usuarios;

-- =====================================================
-- 1. INSERTAR USUARIOS
-- =====================================================
INSERT INTO usuarios (nombre, correo, contrasena, edad, altura, peso_inicial, fecha_registro) VALUES
('Juan Pérez', 'juan.perez@email.com', 'password123', 28, 1.75, 80.5, '2024-01-15'),
('María García', 'maria.garcia@email.com', 'password123', 25, 1.65, 65.0, '2024-01-20'),
('Carlos López', 'carlos.lopez@email.com', 'password123', 32, 1.80, 85.2, '2024-02-01'),
('Ana Martínez', 'ana.martinez@email.com', 'password123', 29, 1.70, 70.8, '2024-02-10'),
('Luis Rodríguez', 'luis.rodriguez@email.com', 'password123', 35, 1.78, 90.0, '2024-02-15'),
('Sofia Herrera', 'sofia.herrera@email.com', 'password123', 26, 1.62, 58.5, '2024-02-20'),
('Diego Morales', 'diego.morales@email.com', 'password123', 30, 1.85, 88.7, '2024-03-01'),
('Valentina Cruz', 'valentina.cruz@email.com', 'password123', 24, 1.68, 62.3, '2024-03-05');

-- =====================================================
-- 2. INSERTAR ENTRENADORES
-- =====================================================
INSERT INTO entrenadores (nombre, correo, contrasena, especialidad) VALUES
('Roberto Silva', 'roberto.silva@fitmanager.com', 'trainer123', 'Musculación'),
('Carmen Vega', 'carmen.vega@fitmanager.com', 'trainer123', 'Cardio y Pérdida de Peso'),
('Miguel Torres', 'miguel.torres@fitmanager.com', 'trainer123', 'Crossfit'),
('Laura Jiménez', 'laura.jimenez@fitmanager.com', 'trainer123', 'Yoga y Flexibilidad'),
('Andrés Ruiz', 'andres.ruiz@fitmanager.com', 'trainer123', 'Fuerza y Potencia'),
('Patricia Moreno', 'patricia.moreno@fitmanager.com', 'trainer123', 'Rehabilitación y Fisioterapia');

-- =====================================================
-- 3. INSERTAR EJERCICIOS
-- =====================================================
INSERT INTO ejercicios (nombre_ejercicio, descripcion, grupo_muscular) VALUES
-- Ejercicios de Pecho
('Press de Banca', 'Ejercicio básico para desarrollar el pecho, hombros y tríceps', 'Pecho'),
('Flexiones', 'Ejercicio de peso corporal para fortalecer pecho, hombros y tríceps', 'Pecho'),
('Aperturas con Mancuernas', 'Ejercicio de aislamiento para el pecho', 'Pecho'),
('Press Inclinado', 'Variación del press de banca con banco inclinado', 'Pecho'),

-- Ejercicios de Espalda
('Dominadas', 'Ejercicio de peso corporal para espalda y bíceps', 'Espalda'),
('Remo con Barra', 'Ejercicio fundamental para el desarrollo de la espalda', 'Espalda'),
('Jalones al Pecho', 'Ejercicio de máquina para espalda ancha', 'Espalda'),
('Peso Muerto', 'Ejercicio compuesto para espalda baja y glúteos', 'Espalda'),

-- Ejercicios de Piernas
('Sentadillas', 'Ejercicio fundamental para piernas y glúteos', 'Piernas'),
('Prensa de Piernas', 'Ejercicio de máquina para cuádriceps', 'Piernas'),
('Zancadas', 'Ejercicio unilateral para piernas', 'Piernas'),
('Curl de Piernas', 'Ejercicio de aislamiento para isquiotibiales', 'Piernas'),

-- Ejercicios de Hombros
('Press Militar', 'Ejercicio para hombros y estabilidad del core', 'Hombros'),
('Elevaciones Laterales', 'Ejercicio de aislamiento para deltoides laterales', 'Hombros'),
('Elevaciones Frontales', 'Ejercicio para deltoides frontales', 'Hombros'),

-- Ejercicios de Brazos
('Curl de Bíceps', 'Ejercicio básico para bíceps', 'Brazos'),
('Extensiones de Tríceps', 'Ejercicio para tríceps', 'Brazos'),
('Martillo', 'Variación del curl de bíceps', 'Brazos'),

-- Ejercicios de Core
('Plancha', 'Ejercicio isométrico para core', 'Core'),
('Abdominales', 'Ejercicio básico para abdominales', 'Core'),
('Mountain Climbers', 'Ejercicio dinámico para core y cardio', 'Core'),

-- Ejercicios Cardiovasculares
('Correr', 'Ejercicio cardiovascular de alta intensidad', 'Cardio'),
('Bicicleta', 'Ejercicio cardiovascular de bajo impacto', 'Cardio'),
('Burpees', 'Ejercicio funcional completo', 'Cardio');

-- =====================================================
-- 4. INSERTAR RUTINAS
-- =====================================================
INSERT INTO rutinas (nombre_rutina, descripcion, id_usuario, id_entrenador) VALUES
-- Rutinas para Juan Pérez (Usuario 1)
('Rutina de Principiante', 'Rutina básica para comenzar en el gimnasio', 1, 1),
('Rutina de Fuerza', 'Enfoque en desarrollo de fuerza máxima', 1, 1),

-- Rutinas para María García (Usuario 2)
('Rutina de Pérdida de Peso', 'Rutina enfocada en quema de grasa', 2, 2),
('Rutina de Cardio', 'Rutina cardiovascular intensa', 2, 2),

-- Rutinas para Carlos López (Usuario 3)
('Rutina Crossfit', 'Rutina de alta intensidad funcional', 3, 3),
('Rutina de Fuerza Avanzada', 'Rutina para atletas experimentados', 3, 1),

-- Rutinas para Ana Martínez (Usuario 4)
('Rutina de Yoga', 'Rutina de flexibilidad y relajación', 4, 4),
('Rutina de Tonificación', 'Rutina para tonificar y definir', 4, 2),

-- Rutinas para Luis Rodríguez (Usuario 5)
('Rutina de Potencia', 'Rutina enfocada en explosividad', 5, 5),
('Rutina de Rehabilitación', 'Rutina para recuperación de lesiones', 5, 6),

-- Rutinas para Sofia Herrera (Usuario 6)
('Rutina de Principiante Femenina', 'Rutina adaptada para mujeres principiantes', 6, 4),
('Rutina de Glúteos', 'Rutina específica para glúteos', 6, 2),

-- Rutinas para Diego Morales (Usuario 7)
('Rutina de Volumen', 'Rutina para ganancia de masa muscular', 7, 1),
('Rutina de Definición', 'Rutina para definición muscular', 7, 5),

-- Rutinas para Valentina Cruz (Usuario 8)
('Rutina de Mantenimiento', 'Rutina para mantener la forma física', 8, 4),
('Rutina de Resistencia', 'Rutina para mejorar la resistencia', 8, 2);

-- =====================================================
-- 5. INSERTAR DETALLES DE RUTINA
-- =====================================================
INSERT INTO detalle de rutina (repeticiones, series, id_rutina, id_ejercicio) VALUES
-- Detalles para Rutina de Principiante (Rutina 1)
(10, 3, 1, 1),  -- Press de Banca
(8, 3, 1, 9),   -- Sentadillas
(12, 3, 1, 5),  -- Dominadas
(15, 3, 1, 17), -- Curl de Bíceps
(20, 3, 1, 19), -- Plancha

-- Detalles para Rutina de Fuerza (Rutina 2)
(5, 5, 2, 1),   -- Press de Banca
(5, 5, 2, 8),   -- Peso Muerto
(5, 5, 2, 9),   -- Sentadillas
(5, 5, 2, 12),  -- Press Militar

-- Detalles para Rutina de Pérdida de Peso (Rutina 3)
(15, 4, 3, 2),  -- Flexiones
(20, 4, 3, 10), -- Prensa de Piernas
(12, 4, 3, 6),  -- Remo con Barra
(30, 3, 3, 25), -- Burpees
(45, 3, 3, 19), -- Plancha

-- Detalles para Rutina Crossfit (Rutina 5)
(21, 1, 5, 25), -- Burpees
(15, 1, 5, 1),  -- Press de Banca
(9, 1, 5, 5),   -- Dominadas
(21, 1, 5, 9),  -- Sentadillas

-- Detalles para Rutina de Yoga (Rutina 7)
(1, 1, 7, 19),  -- Plancha (modificada para yoga)
(1, 1, 7, 20),  -- Abdominales (modificada para yoga)

-- Detalles para Rutina de Potencia (Rutina 9)
(8, 4, 9, 9),   -- Sentadillas
(6, 4, 9, 1),   -- Press de Banca
(8, 4, 9, 12),  -- Press Militar
(10, 4, 9, 8);  -- Peso Muerto

-- =====================================================
-- 6. INSERTAR PROGRESOS
-- =====================================================
INSERT INTO progresos (fecha, peso, medida_pecho, medida_cintura, medida_brazo, id_usuario) VALUES
-- Progresos para Juan Pérez (Usuario 1)
('2024-01-15', 80.5, 95.0, 85.0, 32.0, 1),
('2024-01-22', 80.2, 95.5, 84.5, 32.2, 1),
('2024-01-29', 79.8, 96.0, 84.0, 32.5, 1),
('2024-02-05', 79.5, 96.5, 83.5, 32.8, 1),
('2024-02-12', 79.0, 97.0, 83.0, 33.0, 1),

-- Progresos para María García (Usuario 2)
('2024-01-20', 65.0, 88.0, 70.0, 28.0, 2),
('2024-01-27', 64.5, 88.5, 69.5, 28.2, 2),
('2024-02-03', 64.0, 89.0, 69.0, 28.5, 2),
('2024-02-10', 63.5, 89.5, 68.5, 28.8, 2),
('2024-02-17', 63.0, 90.0, 68.0, 29.0, 2),

-- Progresos para Carlos López (Usuario 3)
('2024-02-01', 85.2, 100.0, 90.0, 35.0, 3),
('2024-02-08', 85.0, 100.5, 89.5, 35.2, 3),
('2024-02-15', 84.8, 101.0, 89.0, 35.5, 3),
('2024-02-22', 84.5, 101.5, 88.5, 35.8, 3),
('2024-03-01', 84.0, 102.0, 88.0, 36.0, 3),

-- Progresos para Ana Martínez (Usuario 4)
('2024-02-10', 70.8, 92.0, 75.0, 30.0, 4),
('2024-02-17', 70.5, 92.5, 74.5, 30.2, 4),
('2024-02-24', 70.2, 93.0, 74.0, 30.5, 4),
('2024-03-03', 70.0, 93.5, 73.5, 30.8, 4),

-- Progresos para Luis Rodríguez (Usuario 5)
('2024-02-15', 90.0, 105.0, 95.0, 37.0, 5),
('2024-02-22', 89.5, 105.5, 94.5, 37.2, 5),
('2024-03-01', 89.0, 106.0, 94.0, 37.5, 5),
('2024-03-08', 88.5, 106.5, 93.5, 37.8, 5),

-- Progresos para Sofia Herrera (Usuario 6)
('2024-02-20', 58.5, 85.0, 65.0, 26.0, 6),
('2024-02-27', 58.2, 85.5, 64.5, 26.2, 6),
('2024-03-05', 58.0, 86.0, 64.0, 26.5, 6),
('2024-03-12', 57.8, 86.5, 63.5, 26.8, 6),

-- Progresos para Diego Morales (Usuario 7)
('2024-03-01', 88.7, 103.0, 92.0, 36.0, 7),
('2024-03-08', 88.5, 103.5, 91.5, 36.2, 7),
('2024-03-15', 88.2, 104.0, 91.0, 36.5, 7),

-- Progresos para Valentina Cruz (Usuario 8)
('2024-03-05', 62.3, 87.0, 67.0, 27.0, 8),
('2024-03-12', 62.0, 87.5, 66.5, 27.2, 8),
('2024-03-19', 61.8, 88.0, 66.0, 27.5, 8);

-- =====================================================
-- 7. INSERTAR PAGOS
-- =====================================================
INSERT INTO pagos (fecha_pago, monto, metodo_pago, estado, tipo_suscripcion, id_usuario, id_entrenador) VALUES
-- Pagos para Juan Pérez (Usuario 1) con Roberto Silva (Entrenador 1)
('2024-01-15', 50.00, 'Tarjeta', 'Completado', 'Premium', 1, 1),
('2024-02-15', 50.00, 'Tarjeta', 'Completado', 'Premium', 1, 1),
('2024-03-15', 50.00, 'Tarjeta', 'Pendiente', 'Premium', 1, 1),

-- Pagos para María García (Usuario 2) con Carmen Vega (Entrenador 2)
('2024-01-20', 45.00, 'Efectivo', 'Completado', 'Básico', 2, 2),
('2024-02-20', 45.00, 'Efectivo', 'Completado', 'Básico', 2, 2),
('2024-03-20', 45.00, 'Efectivo', 'Completado', 'Básico', 2, 2),

-- Pagos para Carlos López (Usuario 3) con Miguel Torres (Entrenador 3)
('2024-02-01', 60.00, 'Tarjeta', 'Completado', 'Entrenador', 3, 3),
('2024-03-01', 60.00, 'Tarjeta', 'Completado', 'Entrenador', 3, 3),

-- Pagos para Ana Martínez (Usuario 4) con Laura Jiménez (Entrenador 4)
('2024-02-10', 40.00, 'Transferencia', 'Completado', 'Básico', 4, 4),
('2024-03-10', 40.00, 'Transferencia', 'Completado', 'Básico', 4, 4),

-- Pagos para Luis Rodríguez (Usuario 5) con Andrés Ruiz (Entrenador 5)
('2024-02-15', 55.00, 'Tarjeta', 'Completado', 'Premium', 5, 5),
('2024-03-15', 55.00, 'Tarjeta', 'Completado', 'Premium', 5, 5),

-- Pagos para Sofia Herrera (Usuario 6) con Patricia Moreno (Entrenador 6)
('2024-02-20', 35.00, 'Efectivo', 'Completado', 'Básico', 6, 6),
('2024-03-20', 35.00, 'Efectivo', 'Pendiente', 'Básico', 6, 6),

-- Pagos para Diego Morales (Usuario 7) con Roberto Silva (Entrenador 1)
('2024-03-01', 50.00, 'Tarjeta', 'Completado', 'Premium', 7, 1),
('2024-03-15', 50.00, 'Tarjeta', 'Rechazado', 'Premium', 7, 1),

-- Pagos para Valentina Cruz (Usuario 8) con Carmen Vega (Entrenador 2)
('2024-03-05', 45.00, 'Transferencia', 'Completado', 'Básico', 8, 2);

-- =====================================================
-- CONSULTAS DE VERIFICACIÓN
-- =====================================================
-- Descomenta las siguientes líneas para verificar los datos insertados:

-- SELECT 'USUARIOS' as tabla, COUNT(*) as total FROM usuarios;
-- SELECT 'ENTRENADORES' as tabla, COUNT(*) as total FROM entrenadores;
-- SELECT 'EJERCICIOS' as tabla, COUNT(*) as total FROM ejercicios;
-- SELECT 'RUTINAS' as tabla, COUNT(*) as total FROM rutinas;
-- SELECT 'DETALLES RUTINA' as tabla, COUNT(*) as total FROM detalle_rutina;
-- SELECT 'PROGRESOS' as tabla, COUNT(*) as total FROM progresos;
-- SELECT 'PAGOS' as tabla, COUNT(*) as total FROM pagos;

-- =====================================================
-- DATOS DE PRUEBA PARA ENDPOINTS
-- =====================================================
--
-- ENDPOINTS DE USUARIO:
-- GET /api/usuarios - Ver todos los usuarios
-- GET /api/usuarios/buscar/1 - Ver usuario específico
-- POST /api/usuarios/login - Login con: {"correo": "juan.perez@email.com", "contrasena": "password123"}
--
-- ENDPOINTS DE ENTRENADOR:
-- GET /api/entrenadores - Ver todos los entrenadores
-- GET /api/entrenadores/buscar/1 - Ver entrenador específico
-- POST /api/entrenadores/login - Login con: {"correo": "roberto.silva@fitmanager.com", "contrasena": "trainer123"}
--
-- ENDPOINTS DE EJERCICIO:
-- GET /api/ejercicios - Ver todos los ejercicios
-- GET /api/ejercicios/categoria/Pecho - Ver ejercicios de pecho
--
-- ENDPOINTS DE RUTINA:
-- GET /api/rutinas - Ver todas las rutinas
-- GET /api/rutinas/usuario/1 - Ver rutinas del usuario 1
-- GET /api/rutinas/entrenador/1 - Ver rutinas del entrenador 1
--
-- ENDPOINTS DE DETALLE RUTINA:
-- GET /api/detalle-rutina - Ver todos los detalles
-- GET /api/detalle-rutina/rutina/1 - Ver detalles de la rutina 1
--
-- ENDPOINTS DE PROGRESO:
-- GET /api/progresos - Ver todos los progresos
-- GET /api/progresos/usuario/1 - Ver progresos del usuario 1
-- GET /api/progresos/fecha/2024-01-15 - Ver progresos de una fecha
--
-- ENDPOINTS DE PAGO:
-- GET /api/pagos - Ver todos los pagos
-- GET /api/pagos/usuario/1 - Ver pagos del usuario 1
-- GET /api/pagos/estado/Completado - Ver pagos completados
--
-- =====================================================


