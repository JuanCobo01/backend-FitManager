-- ================================================================
-- ARCHIVO DE DATOS DE PRUEBA PARA FITMANAGER
-- ================================================================
-- Este archivo se ejecuta automáticamente al iniciar la aplicación
-- para poblar la base de datos con datos de prueba

-- ================================================================
-- 1. INSERTAR ADMINISTRADORES
-- ================================================================
INSERT INTO administradores (nombre, correo, contrasena, rol) VALUES
('Carlos Administrador', 'admin@fitmanager.com', 'admin123', 'SUPER_ADMIN'),
('María Gestora', 'maria.gestora@fitmanager.com', 'maria123', 'ADMIN'),
('Roberto Manager', 'roberto@fitmanager.com', 'roberto123', 'ADMIN');

-- ================================================================
-- 2. INSERTAR GIMNASIOS
-- ================================================================
INSERT INTO gimnasios (nombre, direccion, telefono, id_admin) VALUES
('FitManager Central', 'Calle 50 #25-30, Bogotá', '+57 1 234-5678', 1),
('PowerGym Norte', 'Carrera 15 #85-40, Bogotá', '+57 1 987-6543', 2),
('Elite Fitness', 'Avenida 68 #45-25, Bogotá', '+57 1 555-0123', 3);

-- ================================================================
-- 3. INSERTAR ENTRENADORES
-- ================================================================
INSERT INTO entrenadores (nombre, correo, contrasena, especialidad, id_gimnasio) VALUES
('Juan Carlos Fitness', 'juan.trainer@fitmanager.com', 'juan123', 'Musculación y Fuerza', 1),
('Ana María Sport', 'ana.trainer@fitmanager.com', 'ana123', 'Cardio y Resistencia', 1),
('Pedro Hernández', 'pedro.trainer@fitmanager.com', 'pedro123', 'Funcional y CrossFit', 2),
('Laura Vásquez', 'laura.trainer@fitmanager.com', 'laura123', 'Yoga y Pilates', 2),
('Miguel Torres', 'miguel.trainer@fitmanager.com', 'miguel123', 'Powerlifting', 3),
('Sofia Ramírez', 'sofia.trainer@fitmanager.com', 'sofia123', 'Fitness Femenino', 3);

-- ================================================================
-- 4. INSERTAR USUARIOS/CLIENTES
-- ================================================================
INSERT INTO usuarios (nombre, correo, contrasena, edad, altura, peso_inicial, fecha_registro, id_gimnasio) VALUES
-- Usuarios de FitManager Central
('Andrés López', 'andres.lopez@email.com', 'andres123', 28, 1.75, 80.5, '2024-01-15', 1),
('Camila Rodríguez', 'camila.rodriguez@email.com', 'camila123', 25, 1.65, 62.0, '2024-01-20', 1),
('David Martínez', 'david.martinez@email.com', 'david123', 32, 1.80, 85.0, '2024-02-01', 1),
('Isabella García', 'isabella.garcia@email.com', 'isabella123', 29, 1.68, 58.5, '2024-02-10', 1),

-- Usuarios de PowerGym Norte
('Carlos Sánchez', 'carlos.sanchez@email.com', 'carlos123', 35, 1.78, 90.0, '2024-01-25', 2),
('Valentina Cruz', 'valentina.cruz@email.com', 'valentina123', 26, 1.62, 55.0, '2024-02-05', 2),
('Fernando Jiménez', 'fernando.jimenez@email.com', 'fernando123', 31, 1.82, 88.5, '2024-02-15', 2),
('Natalia Moreno', 'natalia.moreno@email.com', 'natalia123', 27, 1.70, 60.0, '2024-02-20', 2),

-- Usuarios de Elite Fitness
('Sebastián Díaz', 'sebastian.diaz@email.com', 'sebastian123', 30, 1.76, 78.0, '2024-01-30', 3),
('Andrea Castillo', 'andrea.castillo@email.com', 'andrea123', 24, 1.66, 57.5, '2024-02-08', 3),
('Ricardo Herrera', 'ricardo.herrera@email.com', 'ricardo123', 33, 1.79, 82.0, '2024-02-12', 3),
('Daniela Ruiz', 'daniela.ruiz@email.com', 'daniela123', 28, 1.63, 59.0, '2024-02-18', 3);

-- ================================================================
-- 5. INSERTAR EJERCICIOS
-- ================================================================
INSERT INTO ejercicios (nombre_ejercicio, descripcion, detalles, grupo_muscular) VALUES
-- Ejercicios de Pecho
('Press de Banca', 'Ejercicio fundamental para el desarrollo del pecho', 'Acostado en banco, empujar la barra desde el pecho hacia arriba', 'Pecho'),
('Press Inclinado', 'Variación del press para la parte superior del pecho', 'En banco inclinado a 45 grados', 'Pecho'),
('Flexiones', 'Ejercicio de peso corporal para pecho', 'Posición de plancha, bajar y subir el cuerpo', 'Pecho'),

-- Ejercicios de Espalda
('Dominadas', 'Ejercicio de tracción para espalda', 'Colgado de barra, subir hasta barbilla sobre barra', 'Espalda'),
('Remo con Barra', 'Ejercicio de tracción horizontal', 'Inclinado hacia adelante, tirar barra hacia abdomen', 'Espalda'),
('Peso Muerto', 'Ejercicio compuesto fundamental', 'Levantar barra desde el suelo hasta posición erguida', 'Espalda'),

-- Ejercicios de Piernas
('Sentadillas', 'Ejercicio fundamental para piernas', 'Bajar como si fueras a sentarte y subir', 'Piernas'),
('Prensa de Piernas', 'Ejercicio en máquina para cuádriceps', 'Empujar peso con los pies en la máquina', 'Piernas'),
('Zancadas', 'Ejercicio unilateral para piernas', 'Paso largo hacia adelante, bajar y subir', 'Piernas'),

-- Ejercicios de Brazos
('Curl de Bíceps', 'Ejercicio de aislamiento para bíceps', 'Flexión del antebrazo con mancuernas', 'Brazos'),
('Press Francés', 'Ejercicio para tríceps', 'Extensión de brazos por encima de la cabeza', 'Brazos'),
('Fondos en Paralelas', 'Ejercicio compuesto para tríceps', 'Bajar y subir el cuerpo entre barras paralelas', 'Brazos');

-- ================================================================
-- 6. INSERTAR RUTINAS
-- ================================================================
INSERT INTO rutinas (nombre_rutina, descripcion, id_usuario, id_entrenador) VALUES
-- Rutinas para usuarios de FitManager Central
('Rutina Fuerza Básica - Andrés', 'Rutina de fuerza para principiante', 1, 1),
('Rutina Cardio - Camila', 'Rutina enfocada en resistencia cardiovascular', 2, 2),
('Rutina Hipertrofia - David', 'Rutina para ganancia de masa muscular', 3, 1),
('Rutina Tonificación - Isabella', 'Rutina para tonificar y definir músculos', 4, 2),

-- Rutinas para usuarios de PowerGym Norte
('Rutina Funcional - Carlos', 'Entrenamiento funcional completo', 5, 3),
('Rutina Pilates - Valentina', 'Rutina de fortalecimiento y flexibilidad', 6, 4),
('Rutina CrossFit - Fernando', 'Entrenamiento de alta intensidad', 7, 3),
('Rutina Yoga - Natalia', 'Rutina de yoga y relajación', 8, 4),

-- Rutinas para usuarios de Elite Fitness
('Rutina Powerlifting - Sebastián', 'Rutina enfocada en levantamiento de potencia', 9, 5),
('Rutina Fitness Femenino - Andrea', 'Rutina específica para mujeres', 10, 6),
('Rutina Fuerza Avanzada - Ricardo', 'Rutina de fuerza para nivel avanzado', 11, 5),
('Rutina Definición - Daniela', 'Rutina para definición muscular', 12, 6);

-- ================================================================
-- 7. INSERTAR DETALLES DE RUTINAS
-- ================================================================
INSERT INTO detalle_rutina (repeticiones, series, id_rutina, id_ejercicio) VALUES
-- Rutina Fuerza Básica - Andrés (Rutina 1)
(12, 3, 1, 1), -- Press de Banca
(10, 3, 1, 4), -- Dominadas
(15, 3, 1, 7), -- Sentadillas
(12, 3, 1, 10), -- Curl de Bíceps

-- Rutina Cardio - Camila (Rutina 2)
(20, 4, 2, 3), -- Flexiones
(15, 4, 2, 9), -- Zancadas
(25, 3, 2, 7), -- Sentadillas

-- Rutina Hipertrofia - David (Rutina 3)
(8, 4, 3, 1), -- Press de Banca
(8, 4, 3, 2), -- Press Inclinado
(10, 4, 3, 5), -- Remo con Barra
(12, 4, 3, 8), -- Prensa de Piernas

-- Rutina Tonificación - Isabella (Rutina 4)
(15, 3, 4, 3), -- Flexiones
(12, 3, 4, 9), -- Zancadas
(15, 3, 4, 10), -- Curl de Bíceps

-- Rutina Funcional - Carlos (Rutina 5)
(10, 4, 5, 6), -- Peso Muerto
(12, 4, 5, 7), -- Sentadillas
(8, 4, 5, 4), -- Dominadas
(15, 3, 5, 12); -- Fondos en Paralelas

-- ================================================================
-- 8. INSERTAR PROGRESOS
-- ================================================================
INSERT INTO progresos (fecha, peso, medida_pecho, medida_cintura, medida_brazo, id_usuario) VALUES
-- Progresos de Andrés López
('2024-01-15', 80.5, 95.0, 85.0, 32.0, 1), -- Medición inicial
('2024-02-15', 81.2, 96.5, 84.0, 32.5, 1), -- Después de 1 mes
('2024-03-15', 82.0, 98.0, 83.0, 33.0, 1), -- Después de 2 meses

-- Progresos de Camila Rodríguez
('2024-01-20', 62.0, 85.0, 68.0, 25.0, 2), -- Medición inicial
('2024-02-20', 61.5, 84.0, 66.5, 25.2, 2), -- Después de 1 mes
('2024-03-20', 61.0, 83.5, 65.0, 25.5, 2), -- Después de 2 meses

-- Progresos de David Martínez
('2024-02-01', 85.0, 102.0, 90.0, 35.0, 3), -- Medición inicial
('2024-03-01', 86.5, 104.0, 89.0, 36.0, 3), -- Después de 1 mes

-- Progresos de Isabella García
('2024-02-10', 58.5, 82.0, 65.0, 24.0, 4), -- Medición inicial
('2024-03-10', 59.0, 83.0, 64.0, 24.5, 4); -- Después de 1 mes

-- ================================================================
-- 9. INSERTAR PAGOS
-- ================================================================
INSERT INTO pagos (fecha_pago, monto, metodo_pago, estado, tipo_suscripcion, id_usuario, id_entrenador) VALUES
-- Pagos de usuarios de FitManager Central
('2024-01-15', 150000.00, 'Tarjeta de Crédito', 'Pagado', 'Mensual', 1, 1),
('2024-02-15', 150000.00, 'Transferencia', 'Pagado', 'Mensual', 1, 1),
('2024-01-20', 450000.00, 'Efectivo', 'Pagado', 'Trimestral', 2, 2),
('2024-02-01', 300000.00, 'Tarjeta de Débito', 'Pagado', 'Bimensual', 3, 1),
('2024-02-10', 150000.00, 'Transferencia', 'Pagado', 'Mensual', 4, 2),

-- Pagos de usuarios de PowerGym Norte
('2024-01-25', 180000.00, 'Tarjeta de Crédito', 'Pagado', 'Mensual', 5, 3),
('2024-02-05', 500000.00, 'Transferencia', 'Pagado', 'Trimestral', 6, 4),
('2024-02-15', 180000.00, 'Efectivo', 'Pagado', 'Mensual', 7, 3),
('2024-02-20', 350000.00, 'Tarjeta de Débito', 'Pagado', 'Bimensual', 8, 4),

-- Pagos de usuarios de Elite Fitness
('2024-01-30', 200000.00, 'Tarjeta de Crédito', 'Pagado', 'Mensual', 9, 5),
('2024-02-08', 200000.00, 'Transferencia', 'Pagado', 'Mensual', 10, 6),
('2024-02-12', 550000.00, 'Efectivo', 'Pagado', 'Trimestral', 11, 5),
('2024-02-18', 200000.00, 'Tarjeta de Débito', 'Pagado', 'Mensual', 12, 6);

-- ================================================================
-- RESUMEN DE DATOS INSERTADOS:
-- ================================================================
-- ✅ 3 Administradores
-- ✅ 3 Gimnasios
-- ✅ 6 Entrenadores (2 por gimnasio)
-- ✅ 12 Usuarios/Clientes (4 por gimnasio)
-- ✅ 12 Ejercicios (variedad de grupos musculares)
-- ✅ 12 Rutinas (1 por usuario)
-- ✅ 20 Detalles de rutina (ejercicios específicos por rutina)
-- ✅ 8 Registros de progreso (de 4 usuarios diferentes)
-- ✅ 12 Pagos (historial completo de todos los usuarios)
-- ================================================================
