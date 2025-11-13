-- ============================================
-- SCRIPT DE DATOS INICIALES - RUTINAS FITMANAGER
-- ============================================
-- Este script crea 4 rutinas predefinidas con sus ejercicios completos
-- Incluye instrucciones paso a paso y tips para cada ejercicio

-- ============================================
-- 1. RUTINA DE PECHO
-- ============================================

-- Insertar rutina de Pecho
INSERT INTO rutinas (nombre_rutina, descripcion, dificultad, duracion_minutos, objetivo, icono)
VALUES (
    'Rutina de Pecho',
    'Desarrollo completo del pecho con enfoque en volumen muscular y definición. Ideal para construir masa y fuerza en toda la región pectoral.',
    'Intermedio',
    45,
    'Hipertrofia y Fuerza',
    'fitness_center'
);

-- Ejercicios de la rutina de Pecho

-- 1.1 Press Banca Plano
INSERT INTO ejercicios (nombre_ejercicio, descripcion, grupo_muscular, maquina)
VALUES (
    'Press Banca Plano',
    'Ejercicio fundamental para desarrollo del pecho central',
    'Pecho',
    'Banco plano con barra'
);

INSERT INTO ejercicio_instrucciones (ejercicio_id, orden, texto)
VALUES 
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Press Banca Plano'), 1, 'Acuéstate en el banco con los pies firmes en el suelo'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Press Banca Plano'), 2, 'Agarra la barra con un agarre ligeramente más ancho que tus hombros'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Press Banca Plano'), 3, 'Baja la barra de forma controlada hasta que toque ligeramente tu pecho'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Press Banca Plano'), 4, 'Empuja la barra hacia arriba hasta extender completamente los brazos'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Press Banca Plano'), 5, 'Mantén los omóplatos retraídos durante todo el movimiento');

INSERT INTO ejercicio_tips (ejercicio_id, texto)
VALUES 
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Press Banca Plano'), 'Mantén el core activado para proteger la espalda baja'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Press Banca Plano'), 'No rebotes la barra en el pecho, controla el movimiento'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Press Banca Plano'), 'Exhala al empujar e inhala al bajar');

INSERT INTO detalle_rutina (id_rutina, id_ejercicio, orden, series, repeticiones, descanso_segundos)
VALUES (
    (SELECT id_rutina FROM rutinas WHERE nombre_rutina = 'Rutina de Pecho'),
    (SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Press Banca Plano'),
    1, 4, '8-10', 90
);

-- 1.2 Press Banca Inclinado
INSERT INTO ejercicios (nombre_ejercicio, descripcion, grupo_muscular, maquina)
VALUES (
    'Press Banca Inclinado',
    'Enfocado en el desarrollo del pecho superior',
    'Pecho',
    'Banco inclinado 30-45° con barra'
);

INSERT INTO ejercicio_instrucciones (ejercicio_id, orden, texto)
VALUES 
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Press Banca Inclinado'), 1, 'Ajusta el banco a 30-45 grados de inclinación'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Press Banca Inclinado'), 2, 'Siéntate con la espalda completamente apoyada en el banco'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Press Banca Inclinado'), 3, 'Agarra la barra con agarre medio-amplio'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Press Banca Inclinado'), 4, 'Baja la barra hacia la parte superior del pecho'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Press Banca Inclinado'), 5, 'Empuja hacia arriba manteniendo la trayectoria ligeramente inclinada');

INSERT INTO ejercicio_tips (ejercicio_id, texto)
VALUES 
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Press Banca Inclinado'), 'Mayor inclinación = más trabajo de hombros'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Press Banca Inclinado'), 'Mantén los codos a 45° del cuerpo'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Press Banca Inclinado'), 'No arquees excesivamente la espalda baja');

INSERT INTO detalle_rutina (id_rutina, id_ejercicio, orden, series, repeticiones, descanso_segundos)
VALUES (
    (SELECT id_rutina FROM rutinas WHERE nombre_rutina = 'Rutina de Pecho'),
    (SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Press Banca Inclinado'),
    2, 4, '8-10', 90
);

-- 1.3 Aperturas con Mancuernas
INSERT INTO ejercicios (nombre_ejercicio, descripcion, grupo_muscular, maquina)
VALUES (
    'Aperturas con Mancuernas',
    'Ejercicio de aislamiento para el estiramiento del pecho',
    'Pecho',
    'Banco plano con mancuernas'
);

INSERT INTO ejercicio_instrucciones (ejercicio_id, orden, texto)
VALUES 
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Aperturas con Mancuernas'), 1, 'Acuéstate en el banco con una mancuerna en cada mano'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Aperturas con Mancuernas'), 2, 'Inicia con los brazos extendidos sobre el pecho, palmas enfrentadas'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Aperturas con Mancuernas'), 3, 'Baja las mancuernas en un amplio arco con ligera flexión de codos'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Aperturas con Mancuernas'), 4, 'Siente el estiramiento en el pecho sin forzar los hombros'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Aperturas con Mancuernas'), 5, 'Regresa las mancuernas a la posición inicial contrayendo el pecho');

INSERT INTO ejercicio_tips (ejercicio_id, texto)
VALUES 
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Aperturas con Mancuernas'), 'Usa peso moderado, el movimiento debe ser controlado'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Aperturas con Mancuernas'), 'Mantén ligera flexión en los codos durante todo el ejercicio'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Aperturas con Mancuernas'), 'Imagina que estás abrazando un barril grande');

INSERT INTO detalle_rutina (id_rutina, id_ejercicio, orden, series, repeticiones, descanso_segundos)
VALUES (
    (SELECT id_rutina FROM rutinas WHERE nombre_rutina = 'Rutina de Pecho'),
    (SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Aperturas con Mancuernas'),
    3, 3, '12-15', 60
);

-- 1.4 Fondos en Paralelas
INSERT INTO ejercicios (nombre_ejercicio, descripcion, grupo_muscular, maquina)
VALUES (
    'Fondos en Paralelas',
    'Ejercicio compuesto para pecho inferior y tríceps',
    'Pecho',
    'Barras paralelas'
);

INSERT INTO ejercicio_instrucciones (ejercicio_id, orden, texto)
VALUES 
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Fondos en Paralelas'), 1, 'Agarra las barras paralelas y elévate hasta extensión completa'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Fondos en Paralelas'), 2, 'Inclina el torso hacia adelante unos 30 grados'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Fondos en Paralelas'), 3, 'Baja el cuerpo doblando los codos hasta 90 grados'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Fondos en Paralelas'), 4, 'Mantén los codos ligeramente hacia afuera'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Fondos en Paralelas'), 5, 'Empuja hacia arriba contrayendo el pecho y extendiendo los brazos');

INSERT INTO ejercicio_tips (ejercicio_id, texto)
VALUES 
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Fondos en Paralelas'), 'Más inclinación = más trabajo de pecho'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Fondos en Paralelas'), 'Si es muy difícil, usa banda elástica de asistencia'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Fondos en Paralelas'), 'No bloquees completamente los codos arriba para mantener tensión');

INSERT INTO detalle_rutina (id_rutina, id_ejercicio, orden, series, repeticiones, descanso_segundos)
VALUES (
    (SELECT id_rutina FROM rutinas WHERE nombre_rutina = 'Rutina de Pecho'),
    (SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Fondos en Paralelas'),
    4, 3, '10-12', 75
);

-- ============================================
-- 2. RUTINA DE ESPALDA
-- ============================================

INSERT INTO rutinas (nombre_rutina, descripcion, dificultad, duracion_minutos, objetivo, icono)
VALUES (
    'Rutina de Espalda',
    'Desarrollo completo de la musculatura dorsal. Enfocada en amplitud, grosor y fuerza de toda la región de la espalda.',
    'Intermedio',
    50,
    'Desarrollo Muscular',
    'fitness_center'
);

-- 2.1 Dominadas
INSERT INTO ejercicios (nombre_ejercicio, descripcion, grupo_muscular, maquina)
VALUES (
    'Dominadas',
    'Ejercicio rey para el desarrollo del dorsal ancho',
    'Espalda',
    'Barra fija'
);

INSERT INTO ejercicio_instrucciones (ejercicio_id, orden, texto)
VALUES 
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Dominadas'), 1, 'Agarra la barra con las palmas hacia adelante, agarre amplio'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Dominadas'), 2, 'Cuelga con los brazos completamente extendidos'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Dominadas'), 3, 'Activa los dorsales y tira hacia arriba'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Dominadas'), 4, 'Lleva el pecho hacia la barra, barbilla sobre la barra'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Dominadas'), 5, 'Baja de forma controlada hasta extensión completa');

INSERT INTO ejercicio_tips (ejercicio_id, texto)
VALUES 
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Dominadas'), 'Evita usar impulso con el cuerpo (kipping)'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Dominadas'), 'Piensa en llevar los codos hacia las caderas'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Dominadas'), 'Usa banda elástica si aún no puedes hacer repeticiones completas');

INSERT INTO detalle_rutina (id_rutina, id_ejercicio, orden, series, repeticiones, descanso_segundos)
VALUES (
    (SELECT id_rutina FROM rutinas WHERE nombre_rutina = 'Rutina de Espalda'),
    (SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Dominadas'),
    1, 4, '6-10', 120
);

-- 2.2 Remo con Barra
INSERT INTO ejercicios (nombre_ejercicio, descripcion, grupo_muscular, maquina)
VALUES (
    'Remo con Barra',
    'Ejercicio compuesto para grosor de espalda media',
    'Espalda',
    'Barra olímpica'
);

INSERT INTO ejercicio_instrucciones (ejercicio_id, orden, texto)
VALUES 
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Remo con Barra'), 1, 'Inclínate hacia adelante manteniendo la espalda recta, 45 grados'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Remo con Barra'), 2, 'Agarra la barra con agarre prono (palmas hacia abajo)'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Remo con Barra'), 3, 'Tira de la barra hacia el abdomen bajo'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Remo con Barra'), 4, 'Aprieta los omóplatos en la contracción máxima'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Remo con Barra'), 5, 'Baja la barra de forma controlada sin redondear la espalda');

INSERT INTO ejercicio_tips (ejercicio_id, texto)
VALUES 
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Remo con Barra'), 'Mantén el core activado durante todo el movimiento'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Remo con Barra'), 'No uses impulso de cadera, usa la espalda'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Remo con Barra'), 'Mantén las rodillas ligeramente flexionadas');

INSERT INTO detalle_rutina (id_rutina, id_ejercicio, orden, series, repeticiones, descanso_segundos)
VALUES (
    (SELECT id_rutina FROM rutinas WHERE nombre_rutina = 'Rutina de Espalda'),
    (SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Remo con Barra'),
    2, 4, '8-10', 90
);

-- 2.3 Jalón al Pecho
INSERT INTO ejercicios (nombre_ejercicio, descripcion, grupo_muscular, maquina)
VALUES (
    'Jalón al Pecho',
    'Ejercicio de tracción vertical para dorsales',
    'Espalda',
    'Máquina de poleas alta'
);

INSERT INTO ejercicio_instrucciones (ejercicio_id, orden, texto)
VALUES 
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Jalón al Pecho'), 1, 'Siéntate en la máquina con los muslos asegurados bajo las almohadillas'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Jalón al Pecho'), 2, 'Agarra la barra con agarre amplio, palmas hacia adelante'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Jalón al Pecho'), 3, 'Tira de la barra hacia la parte superior del pecho'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Jalón al Pecho'), 4, 'Inclina ligeramente el torso hacia atrás'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Jalón al Pecho'), 5, 'Regresa la barra de forma controlada a la posición inicial');

INSERT INTO ejercicio_tips (ejercicio_id, texto)
VALUES 
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Jalón al Pecho'), 'No uses impulso corporal excesivo'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Jalón al Pecho'), 'Enfócate en contraer los dorsales'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Jalón al Pecho'), 'Mantén el pecho hacia afuera durante el movimiento');

INSERT INTO detalle_rutina (id_rutina, id_ejercicio, orden, series, repeticiones, descanso_segundos)
VALUES (
    (SELECT id_rutina FROM rutinas WHERE nombre_rutina = 'Rutina de Espalda'),
    (SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Jalón al Pecho'),
    3, 3, '10-12', 75
);

-- 2.4 Peso Muerto Rumano
INSERT INTO ejercicios (nombre_ejercicio, descripcion, grupo_muscular, maquina)
VALUES (
    'Peso Muerto Rumano',
    'Enfocado en espalda baja y cadena posterior',
    'Espalda',
    'Barra olímpica'
);

INSERT INTO ejercicio_instrucciones (ejercicio_id, orden, texto)
VALUES 
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Peso Muerto Rumano'), 1, 'Párate con los pies al ancho de hombros, rodillas ligeramente flexionadas'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Peso Muerto Rumano'), 2, 'Agarra la barra con agarre prono al ancho de hombros'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Peso Muerto Rumano'), 3, 'Baja la barra deslizándola cerca de las piernas, empujando las caderas hacia atrás'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Peso Muerto Rumano'), 4, 'Mantén la espalda recta y neutral durante todo el movimiento'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Peso Muerto Rumano'), 5, 'Regresa empujando las caderas hacia adelante hasta posición inicial');

INSERT INTO ejercicio_tips (ejercicio_id, texto)
VALUES 
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Peso Muerto Rumano'), 'No redondees la espalda baja en ningún momento'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Peso Muerto Rumano'), 'Siente el estiramiento en los isquiotibiales'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Peso Muerto Rumano'), 'Mantén la barra pegada al cuerpo todo el tiempo');

INSERT INTO detalle_rutina (id_rutina, id_ejercicio, orden, series, repeticiones, descanso_segundos)
VALUES (
    (SELECT id_rutina FROM rutinas WHERE nombre_rutina = 'Rutina de Espalda'),
    (SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Peso Muerto Rumano'),
    4, 3, '10-12', 90
);

-- ============================================
-- 3. RUTINA DE PIERNAS
-- ============================================

INSERT INTO rutinas (nombre_rutina, descripcion, dificultad, duracion_minutos, objetivo, icono)
VALUES (
    'Rutina de Piernas',
    'Entrenamiento completo de tren inferior. Enfocado en desarrollo de cuádriceps, glúteos, isquiotibiales y pantorrillas.',
    'Avanzado',
    55,
    'Fuerza e Hipertrofia',
    'fitness_center'
);

-- 3.1 Sentadilla con Barra
INSERT INTO ejercicios (nombre_ejercicio, descripcion, grupo_muscular, maquina)
VALUES (
    'Sentadilla con Barra',
    'Rey de los ejercicios de pierna, desarrollo completo',
    'Piernas',
    'Rack con barra olímpica'
);

INSERT INTO ejercicio_instrucciones (ejercicio_id, orden, texto)
VALUES 
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Sentadilla con Barra'), 1, 'Coloca la barra sobre los trapecios superiores'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Sentadilla con Barra'), 2, 'Separa los pies al ancho de hombros, pies ligeramente hacia afuera'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Sentadilla con Barra'), 3, 'Baja doblando rodillas y caderas, manteniendo el pecho arriba'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Sentadilla con Barra'), 4, 'Desciende hasta que los muslos estén paralelos al suelo o más'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Sentadilla con Barra'), 5, 'Empuja a través de los talones para volver a la posición inicial');

INSERT INTO ejercicio_tips (ejercicio_id, texto)
VALUES 
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Sentadilla con Barra'), 'Mantén las rodillas alineadas con los pies'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Sentadilla con Barra'), 'No dejes que las rodillas se vayan hacia adentro'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Sentadilla con Barra'), 'Respira: inhala al bajar, exhala al subir');

INSERT INTO detalle_rutina (id_rutina, id_ejercicio, orden, series, repeticiones, descanso_segundos)
VALUES (
    (SELECT id_rutina FROM rutinas WHERE nombre_rutina = 'Rutina de Piernas'),
    (SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Sentadilla con Barra'),
    1, 4, '8-10', 120
);

-- 3.2 Prensa de Piernas
INSERT INTO ejercicios (nombre_ejercicio, descripcion, grupo_muscular, maquina)
VALUES (
    'Prensa de Piernas',
    'Ejercicio complementario para cuádriceps y glúteos',
    'Piernas',
    'Máquina prensa 45°'
);

INSERT INTO ejercicio_instrucciones (ejercicio_id, orden, texto)
VALUES 
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Prensa de Piernas'), 1, 'Siéntate en la máquina con la espalda completamente apoyada'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Prensa de Piernas'), 2, 'Coloca los pies en el centro de la plataforma, ancho de hombros'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Prensa de Piernas'), 3, 'Libera los seguros y baja la plataforma doblando las rodillas'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Prensa de Piernas'), 4, 'Baja hasta que tus rodillas formen 90 grados'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Prensa de Piernas'), 5, 'Empuja la plataforma hacia arriba sin bloquear completamente las rodillas');

INSERT INTO ejercicio_tips (ejercicio_id, texto)
VALUES 
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Prensa de Piernas'), 'No despegues la espalda baja del respaldo'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Prensa de Piernas'), 'Mantén las rodillas alineadas con los pies'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Prensa de Piernas'), 'Rango completo de movimiento para mejores resultados');

INSERT INTO detalle_rutina (id_rutina, id_ejercicio, orden, series, repeticiones, descanso_segundos)
VALUES (
    (SELECT id_rutina FROM rutinas WHERE nombre_rutina = 'Rutina de Piernas'),
    (SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Prensa de Piernas'),
    2, 3, '10-12', 90
);

-- 3.3 Peso Muerto con Barra
INSERT INTO ejercicios (nombre_ejercicio, descripcion, grupo_muscular, maquina)
VALUES (
    'Peso Muerto con Barra',
    'Ejercicio compuesto para cadena posterior completa',
    'Piernas',
    'Barra olímpica'
);

INSERT INTO ejercicio_instrucciones (ejercicio_id, orden, texto)
VALUES 
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Peso Muerto con Barra'), 1, 'Párate con la barra sobre el centro del pie'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Peso Muerto con Barra'), 2, 'Agáchate y agarra la barra con agarre al ancho de hombros'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Peso Muerto con Barra'), 3, 'Mantén la espalda neutral, pecho hacia afuera'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Peso Muerto con Barra'), 4, 'Levanta la barra extendiendo caderas y rodillas simultáneamente'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Peso Muerto con Barra'), 5, 'Párate erguido con los hombros hacia atrás, luego baja controladamente');

INSERT INTO ejercicio_tips (ejercicio_id, texto)
VALUES 
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Peso Muerto con Barra'), 'Este es el ejercicio más técnico, aprende la forma correcta primero'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Peso Muerto con Barra'), 'Mantén la barra pegada al cuerpo durante todo el movimiento'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Peso Muerto con Barra'), 'Usa cinturón de levantamiento para cargas pesadas');

INSERT INTO detalle_rutina (id_rutina, id_ejercicio, orden, series, repeticiones, descanso_segundos)
VALUES (
    (SELECT id_rutina FROM rutinas WHERE nombre_rutina = 'Rutina de Piernas'),
    (SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Peso Muerto con Barra'),
    3, 4, '6-8', 150
);

-- 3.4 Curl de Piernas
INSERT INTO ejercicios (nombre_ejercicio, descripcion, grupo_muscular, maquina)
VALUES (
    'Curl de Piernas',
    'Aislamiento de isquiotibiales',
    'Piernas',
    'Máquina de curl acostado'
);

INSERT INTO ejercicio_instrucciones (ejercicio_id, orden, texto)
VALUES 
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Curl de Piernas'), 1, 'Acuéstate boca abajo en la máquina'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Curl de Piernas'), 2, 'Coloca los tobillos bajo el rodillo acolchado'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Curl de Piernas'), 3, 'Agarra las manijas para estabilizarte'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Curl de Piernas'), 4, 'Flexiona las rodillas llevando los talones hacia los glúteos'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Curl de Piernas'), 5, 'Baja de forma controlada sin dejar que el peso toque la pila');

INSERT INTO ejercicio_tips (ejercicio_id, texto)
VALUES 
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Curl de Piernas'), 'No levantes las caderas del banco'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Curl de Piernas'), 'Haz una pausa de 1 segundo en la contracción máxima'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Curl de Piernas'), 'Controla la fase excéntrica (bajada)');

INSERT INTO detalle_rutina (id_rutina, id_ejercicio, orden, series, repeticiones, descanso_segundos)
VALUES (
    (SELECT id_rutina FROM rutinas WHERE nombre_rutina = 'Rutina de Piernas'),
    (SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Curl de Piernas'),
    4, 3, '12-15', 60
);

-- ============================================
-- 4. RUTINA FULL BODY
-- ============================================

INSERT INTO rutinas (nombre_rutina, descripcion, dificultad, duracion_minutos, objetivo, icono)
VALUES (
    'Full Body',
    'Entrenamiento de cuerpo completo ideal para principiantes. Trabaja todos los grupos musculares principales en una sola sesión.',
    'Principiante',
    40,
    'Acondicionamiento General',
    'fitness_center'
);

-- 4.1 Sentadilla Goblet
INSERT INTO ejercicios (nombre_ejercicio, descripcion, grupo_muscular, maquina)
VALUES (
    'Sentadilla Goblet',
    'Variante de sentadilla ideal para aprender la técnica',
    'Piernas',
    'Mancuerna o kettlebell'
);

INSERT INTO ejercicio_instrucciones (ejercicio_id, orden, texto)
VALUES 
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Sentadilla Goblet'), 1, 'Sostén una mancuerna verticalmente frente a tu pecho con ambas manos'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Sentadilla Goblet'), 2, 'Separa los pies ligeramente más anchos que el ancho de hombros'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Sentadilla Goblet'), 3, 'Baja en sentadilla manteniendo el pecho elevado'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Sentadilla Goblet'), 4, 'Baja hasta que los codos toquen la parte interna de las rodillas'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Sentadilla Goblet'), 5, 'Empuja a través de los talones para volver arriba');

INSERT INTO ejercicio_tips (ejercicio_id, texto)
VALUES 
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Sentadilla Goblet'), 'Excelente para aprender movilidad de cadera'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Sentadilla Goblet'), 'Mantén los codos presionando las rodillas hacia afuera'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Sentadilla Goblet'), 'Peso moderado, enfócate en la técnica');

INSERT INTO detalle_rutina (id_rutina, id_ejercicio, orden, series, repeticiones, descanso_segundos)
VALUES (
    (SELECT id_rutina FROM rutinas WHERE nombre_rutina = 'Full Body'),
    (SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Sentadilla Goblet'),
    1, 3, '12-15', 60
);

-- 4.2 Press de Pecho con Mancuernas
INSERT INTO ejercicios (nombre_ejercicio, descripcion, grupo_muscular, maquina)
VALUES (
    'Press de Pecho con Mancuernas',
    'Desarrollo de pecho con mayor rango de movimiento',
    'Pecho',
    'Banco plano con mancuernas'
);

INSERT INTO ejercicio_instrucciones (ejercicio_id, orden, texto)
VALUES 
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Press de Pecho con Mancuernas'), 1, 'Acuéstate en un banco plano con una mancuerna en cada mano'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Press de Pecho con Mancuernas'), 2, 'Posiciona las mancuernas a los lados del pecho'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Press de Pecho con Mancuernas'), 3, 'Empuja las mancuernas hacia arriba hasta extender los brazos'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Press de Pecho con Mancuernas'), 4, 'Mantén las mancuernas paralelas o con ligera rotación'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Press de Pecho con Mancuernas'), 5, 'Baja controladamente hasta sentir estiramiento en el pecho');

INSERT INTO ejercicio_tips (ejercicio_id, texto)
VALUES 
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Press de Pecho con Mancuernas'), 'Mayor rango de movimiento que con barra'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Press de Pecho con Mancuernas'), 'Trabaja cada lado independientemente'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Press de Pecho con Mancuernas'), 'No choques las mancuernas arriba');

INSERT INTO detalle_rutina (id_rutina, id_ejercicio, orden, series, repeticiones, descanso_segundos)
VALUES (
    (SELECT id_rutina FROM rutinas WHERE nombre_rutina = 'Full Body'),
    (SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Press de Pecho con Mancuernas'),
    2, 3, '10-12', 75
);

-- 4.3 Remo con Mancuerna a Una Mano
INSERT INTO ejercicios (nombre_ejercicio, descripcion, grupo_muscular, maquina)
VALUES (
    'Remo con Mancuerna a Una Mano',
    'Ejercicio unilateral para desarrollo de espalda',
    'Espalda',
    'Banco plano con mancuerna'
);

INSERT INTO ejercicio_instrucciones (ejercicio_id, orden, texto)
VALUES 
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Remo con Mancuerna a Una Mano'), 1, 'Apoya una rodilla y una mano en el banco'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Remo con Mancuerna a Una Mano'), 2, 'Mantén la espalda paralela al suelo'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Remo con Mancuerna a Una Mano'), 3, 'Con la mano libre, agarra la mancuerna dejándola colgar'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Remo con Mancuerna a Una Mano'), 4, 'Tira de la mancuerna hacia la cadera, codo cerca del cuerpo'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Remo con Mancuerna a Una Mano'), 5, 'Baja controladamente y repite, luego cambia de lado');

INSERT INTO ejercicio_tips (ejercicio_id, texto)
VALUES 
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Remo con Mancuerna a Una Mano'), 'No rotes el torso durante el movimiento'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Remo con Mancuerna a Una Mano'), 'Piensa en tirar con el codo, no con la mano'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Remo con Mancuerna a Una Mano'), 'Aprieta el omóplato en la contracción máxima');

INSERT INTO detalle_rutina (id_rutina, id_ejercicio, orden, series, repeticiones, descanso_segundos)
VALUES (
    (SELECT id_rutina FROM rutinas WHERE nombre_rutina = 'Full Body'),
    (SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Remo con Mancuerna a Una Mano'),
    3, 3, '10-12', 60
);

-- 4.4 Press de Hombros con Mancuernas
INSERT INTO ejercicios (nombre_ejercicio, descripcion, grupo_muscular, maquina)
VALUES (
    'Press de Hombros con Mancuernas',
    'Desarrollo de deltoides y fuerza de hombros',
    'Hombros',
    'Banco con respaldo 90° y mancuernas'
);

INSERT INTO ejercicio_instrucciones (ejercicio_id, orden, texto)
VALUES 
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Press de Hombros con Mancuernas'), 1, 'Siéntate en un banco con respaldo vertical'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Press de Hombros con Mancuernas'), 2, 'Sostén las mancuernas a la altura de los hombros'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Press de Hombros con Mancuernas'), 3, 'Empuja las mancuernas hacia arriba hasta extensión completa'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Press de Hombros con Mancuernas'), 4, 'No bloquees completamente los codos arriba'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Press de Hombros con Mancuernas'), 5, 'Baja controladamente hasta posición inicial');

INSERT INTO ejercicio_tips (ejercicio_id, texto)
VALUES 
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Press de Hombros con Mancuernas'), 'Mantén el core activado para proteger la espalda'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Press de Hombros con Mancuernas'), 'No arquees excesivamente la espalda baja'),
    ((SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Press de Hombros con Mancuernas'), 'Movimiento vertical, no inclines hacia adelante');

INSERT INTO detalle_rutina (id_rutina, id_ejercicio, orden, series, repeticiones, descanso_segundos)
VALUES (
    (SELECT id_rutina FROM rutinas WHERE nombre_rutina = 'Full Body'),
    (SELECT id_ejercicio FROM ejercicios WHERE nombre_ejercicio = 'Press de Hombros con Mancuernas'),
    4, 3, '10-12', 75
);

-- ============================================
-- FIN DEL SCRIPT
-- ============================================

-- Verificación de datos insertados
SELECT 'RESUMEN DE DATOS INSERTADOS:' as mensaje;
SELECT COUNT(*) as total_rutinas FROM rutinas;
SELECT COUNT(*) as total_ejercicios FROM ejercicios;
SELECT COUNT(*) as total_detalles FROM detalle_rutina;
SELECT COUNT(*) as total_instrucciones FROM ejercicio_instrucciones;
SELECT COUNT(*) as total_tips FROM ejercicio_tips;
