﻿
-- Sprint.
INSERT INTO sprint(id_sprint, nombre, fecha_inicio, fecha_fin, proyecto) VALUES(1, 'Implementacion del Login ', to_date('11-03-18','DD-MM-YY'), to_date('18-03-18','DD-MM-YY'), 1);

--Estado_kanban
INSERT INTO estado_kanban(id_estado_kanban, descripcion) VALUES (1,'Por Hacer');
INSERT INTO estado_kanban(id_estado_kanban, descripcion) VALUES (2,'Haciendo');
INSERT INTO estado_kanban(id_estado_kanban, descripcion) VALUES (3,'Hecho');

--User Histories

