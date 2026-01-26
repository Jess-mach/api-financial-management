CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE public.usuarios(
    id             uuid         NOT NULL DEFAULT gen_random_uuid(),
    email          varchar(255) NOT NULL,
    login          varchar(255) NOT NULL,
    nome           varchar(255) NOT NULL,
    perfil_usuario varchar(255) NULL,
    senha          varchar(255) NOT NULL,
    CONSTRAINT usuarios_perfil_usuario_check CHECK (((perfil_usuario)::text = ANY ((ARRAY['ADMINISTRADOR':: character varying, 'USUARIO':: character varying, 'GERENTE':: character varying])::text[])
) ),
                                     CONSTRAINT uk_user_email UNIQUE (email),
    CONSTRAINT uk_user_login UNIQUE (login),
	CONSTRAINT usuarios_pkey PRIMARY KEY (id)
);