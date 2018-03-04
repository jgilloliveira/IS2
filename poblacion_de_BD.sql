-- Estados.
INSERT INTO estado_proyecto VALUES(1,'Vacio');
INSERT INTO estado_proyecto VALUES(2,'Planificada');
INSERT INTO estado_proyecto VALUES(3,'En Proceso');
INSERT INTO estado_proyecto VALUES(4,'Hecho');
INSERT INTO estado_proyecto VALUES(5,'Descartada');

-- Roles.
INSERT INTO rol_sistema VALUES (1, 'SCRUM MASTER');
INSERT INTO rol_sistema VALUES (3, 'TASK OWNER');
INSERT INTO rol_sistema VALUES (4, 'USER');
INSERT INTO rol_sistema VALUES (5, 'REPRESENTATIVE');
INSERT INTO rol_sistema VALUES (6, 'PROCUREMENT');
INSERT INTO rol_sistema VALUES (7, 'LEGALS');
INSERT INTO rol_sistema VALUES (2, 'PROJECT LEADER');

-- Proyecto.
﻿INSERT INTO proyecto (id_proyecto, nombre, descripcion, fecha_inicio, fecha_fin, estado, cliente) VALUES (1, 'Aplicacion ', 'Aplicacion Movil', to_date('4-03-18','DD-MM-YY'), to_date('4-12-18','DD-MM-YY'), 1, 'Lilian Riveros');

-- Grupo de trabajo.
INSERT INTO grupo_de_trabajo VALUES (1,4,'Team Git',1);

-- Usuarios.
INSERT INTO PUBLIC.USUARIO (id_usuario, nombre, apellido, nombre_usuario, contrasenha, rol, grupo) 
VALUES (1, 'pablo', 'aguilar', 'paguilar', 'cambiar123', 1, 1);
INSERT INTO PUBLIC.USUARIO (id_usuario, nombre, apellido, nombre_usuario, contrasenha, rol, grupo) 
VALUES (2, 'jacqueline', 'gill', 'jgill', 'cambiar123', 2, 1);
INSERT INTO PUBLIC.USUARIO (id_usuario, nombre, apellido, nombre_usuario, contrasenha, rol, grupo) 
VALUES (3, 'oscar', 'vega', 'ovega', 'cambiar123', 3, 1);
INSERT INTO PUBLIC.USUARIO (id_usuario, nombre, apellido, nombre_usuario, contrasenha, rol, grupo) 
VALUES (4, 'porfirio', 'perez', 'pperez', 'cambiar123', 5, 1);
INSERT INTO PUBLIC.USUARIO (id_usuario, nombre, apellido, nombre_usuario, contrasenha, rol, grupo) 
VALUES (5, 'lilian', 'riveros', 'lriveros', 'cambiar123', 4, 1);
INSERT INTO PUBLIC.USUARIO (id_usuario, nombre, apellido, nombre_usuario, contrasenha, rol, grupo) 
VALUES (6, 'lilian', 'demattei', 'ldemattei', 'cambiar123', 4, 1);
INSERT INTO PUBLIC.USUARIO (id_usuario, nombre, apellido, nombre_usuario, contrasenha, rol, grupo) 
VALUES (7, 'sebastian', 'fernandez', 'sfernandez', 'cambiar123', 4, 1);
