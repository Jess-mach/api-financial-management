package br.com.ntt.common.transacao.infra.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface TransacaoRepository extends JpaRepository<TransacaoEntity, UUID> {

    @Query(value = """
             select
                DATE(te1_0.data_hora_solicitacao)\\:\\:text as dataHoraSolicitacao,
                te1_0.tipo\\:\\:integer as tipo,
                sum(te1_0.valor)\\:\\:float as valor,
                count(te1_0.id)\\:\\:integer as quantidade
             from
                transacoes te1_0
             where
                 te1_0.usuario_id\\:\\:uuid = :usuarioId
                 AND te1_0.status\\:\\:integer = 1
             group by
                te1_0.tipo,
                date(te1_0.data_hora_solicitacao)
    """, nativeQuery = true)
    List<AnaliseDeDespesaCampos> visualisarGastosDia(UUID usuarioId);

    @Query(value = """
             select
                   SUBSTRING(DATE_TRUNC('month', t.data_hora_solicitacao)\\:\\:text from 1 for 7) as dataHoraSolicitacao,
                   t.tipo\\:\\:integer as tipo,
                   sum (t.valor)\\:\\:float as valor,
                   count(*)\\:\\:integer as quantidade
             from transacoes t
             where t.usuario_id\\:\\:uuid = ?1
             and t.status\\:\\:integer = 1
             group by t.tipo , DATE_TRUNC('month', t.data_hora_solicitacao)
            """, nativeQuery = true)
    List<AnaliseDeDespesaCampos> visualisarGastosMes(UUID usuarioId);

    List<TransacaoEntity> findAllByUsuarioId(UUID usuarioId);

}
