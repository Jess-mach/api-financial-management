CREATE TABLE if not exists transacoes
(
    id                    UUID           NOT NULL,
    usuario_id            UUID           NOT NULL,
    valor                 DECIMAL(19, 2) NOT NULL,
    tipo                  VARCHAR(50)    NOT NULL,
    status                VARCHAR(50)    NOT NULL,
    data_hora_solicitacao TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    data_hora_finalizacao TIMESTAMP WITHOUT TIME ZONE,
    moeda                 VARCHAR(3),
    taxa_cambio           DECIMAL(19, 6),
    descricao             VARCHAR(255),
    CONSTRAINT pk_transacoes PRIMARY KEY (id)
);

CREATE INDEX idx_transacoes_usuario_id ON transacoes (usuario_id);