drop database if exists practica;
drop user if exists usuario_practica;

create database practica
  default character set utf8mb4
  default collate utf8mb4_unicode_ci;

create user 'usuario_practica'@'%' identified by 'la_Clave';
grant select, insert, update, delete on practica.* to 'usuario_practica'@'%';
flush privileges;

use practica;

create table suculenta (
  id_suculenta INT NOT NULL AUTO_INCREMENT,
  nombre_comun VARCHAR(60) NOT NULL,
  nombre_cientifico VARCHAR(80) NOT NULL,
  familia VARCHAR(50),
  color_principal VARCHAR(30),
  altura_cm DECIMAL(6,2) CHECK (altura_cm >= 0),
  precio_estimado DECIMAL(12,2) CHECK (precio_estimado >= 0),
  nivel_riego VARCHAR(20),
  ruta_imagen VARCHAR(1024),
  activo BOOLEAN,
  fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id_suculenta),
  UNIQUE (nombre_cientifico),
  INDEX ndx_nombre_comun (nombre_comun))
  ENGINE = InnoDB;

-- Datos de prueba
INSERT INTO suculenta
  (nombre_comun, nombre_cientifico, familia, color_principal, altura_cm, precio_estimado, nivel_riego, ruta_imagen, activo)
VALUES
('Planta de jade',        'Crassula ovata',          'Crassulaceae',   'Verde',         80.00, 4500.00,  'Bajo',  'https://images.unsplash.com/photo-1485955900006-10f4d324d411', true),
('Rosa de piedra',        'Echeveria elegans',       'Crassulaceae',   'Verde azulado', 12.50, 3200.00,  'Bajo',  'https://images.unsplash.com/photo-1459411552884-841db9b3cc2a', true),
('Cola de burro',         'Sedum morganianum',       'Crassulaceae',   'Verde claro',   60.00, 5200.00,  'Medio', 'https://images.unsplash.com/photo-1509423350716-97f9360b4e09', true),
('Aloe vera',             'Aloe barbadensis miller', 'Asphodelaceae',  'Verde',         60.00, 3800.00,  'Bajo',  'https://images.unsplash.com/photo-1596547609652-9cf5d8d76921', true),
('Planta zebra',          'Haworthiopsis attenuata', 'Asphodelaceae',  'Verde rayado',  15.00, 2900.00,  'Bajo',  'https://images.unsplash.com/photo-1572688484438-313a6e50c333', true),
('Lengua de suegra',      'Sansevieria trifasciata', 'Asparagaceae',   'Verde oscuro',  90.00, 6100.00,  'Bajo',  'https://images.unsplash.com/photo-1593691509543-c55fb32d8de5', true),
('Orejas de gato',       'Kalanchoe tomentosa',     'Crassulaceae',   'Gris verdoso',  40.00, 3500.00,  'Bajo',  'https://images.unsplash.com/photo-1632207691143-643e2a9a9361', true),
('Collar de perlas',      'Senecio rowleyanus',      'Asteraceae',     'Verde',         30.00, 4800.00,  'Medio', 'https://images.unsplash.com/photo-1459156212016-c812468e2115', false);
