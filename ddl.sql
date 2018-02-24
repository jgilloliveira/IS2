
CREATE TABLE public.estado_kanban (
                id_estado_kanban INTEGER NOT NULL,
                descripcion VARCHAR NOT NULL,
                CONSTRAINT estado_kanban_pk PRIMARY KEY (id_estado_kanban)
);


CREATE TABLE public.estado_proyecto (
                id_estado INTEGER NOT NULL,
                descripcion VARCHAR(20) NOT NULL,
                CONSTRAINT estado_proyecto_pk PRIMARY KEY (id_estado)
);


CREATE TABLE public.proyecto (
                id_proyecto INTEGER NOT NULL,
                nombre VARCHAR(100) NOT NULL,
                descripcion VARCHAR(500) NOT NULL,
                fecha_inicio DATE NOT NULL,
                fecha_fin DATE,
                estado INTEGER NOT NULL,
                cliente VARCHAR(200) NOT NULL,
                CONSTRAINT proyecto_pk PRIMARY KEY (id_proyecto)
);


CREATE TABLE public.grupo_de_trabajo (
                id_grupo INTEGER NOT NULL,
                cantidad INTEGER NOT NULL,
                nombre VARCHAR(20) NOT NULL,
                proyecto_grupo INTEGER NOT NULL,
                CONSTRAINT grupo_de_trabajo_pk PRIMARY KEY (id_grupo)
);


CREATE TABLE public.sprint (
                id_sprint INTEGER NOT NULL,
                nombre VARCHAR(30) NOT NULL,
                fecha_inicio DATE NOT NULL,
                fecha_fin DATE,
                proyecto INTEGER NOT NULL,
                CONSTRAINT sprint_pk PRIMARY KEY (id_sprint)
);


CREATE TABLE public.user_histories (
                id_us INTEGER NOT NULL,
                descripcion VARCHAR(100) NOT NULL,
                estado_kanban INTEGER NOT NULL,
                us_sprint INTEGER NOT NULL,
                CONSTRAINT user_histories_pk PRIMARY KEY (id_us)
);


CREATE TABLE public.rol_sistema (
                id_rol INTEGER NOT NULL,
                descripcion VARCHAR(30) NOT NULL,
                CONSTRAINT rol_sistema_pk PRIMARY KEY (id_rol)
);


CREATE TABLE public.usuario (
                id_usuario INTEGER NOT NULL,
                nombre VARCHAR(100) NOT NULL,
                apellido VARCHAR(100) NOT NULL,
                nombre_usuario VARCHAR(20) NOT NULL,
                contrasenha VARCHAR(10) NOT NULL,
                rol INTEGER NOT NULL,
                grupo INTEGER NOT NULL,
                CONSTRAINT usuario_pk PRIMARY KEY (id_usuario)
);


ALTER TABLE public.user_histories ADD CONSTRAINT estado_kanban_user_histories_fk
FOREIGN KEY (estado_kanban)
REFERENCES public.estado_kanban (id_estado_kanban)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.proyecto ADD CONSTRAINT estado_proyecto_proyecto_fk
FOREIGN KEY (estado)
REFERENCES public.estado_proyecto (id_estado)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.sprint ADD CONSTRAINT proyecto_sprint_fk
FOREIGN KEY (proyecto)
REFERENCES public.proyecto (id_proyecto)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.grupo_de_trabajo ADD CONSTRAINT proyecto_grupo_de_trabajo_fk
FOREIGN KEY (proyecto_grupo)
REFERENCES public.proyecto (id_proyecto)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.usuario ADD CONSTRAINT grupo_de_trabajo_usuario_fk
FOREIGN KEY (grupo)
REFERENCES public.grupo_de_trabajo (id_grupo)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.user_histories ADD CONSTRAINT sprint_user_histories_fk
FOREIGN KEY (us_sprint)
REFERENCES public.sprint (id_sprint)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.usuario ADD CONSTRAINT rol_sistema_usuario_fk
FOREIGN KEY (rol)
REFERENCES public.rol_sistema (id_rol)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;
