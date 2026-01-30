package br.com.ntt.common.transacao.infra.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface TransacaoRepository extends JpaRepository<TransacaoEntity, UUID> {

    @Query(value = """
             select 
                     CAST(DATE(t.dataHoraSolicitacao) AS text) as dataHoraSolicitacao,
                     CAST(t.tipo AS int) as tipo,
                     CAST(sum(t.valor) as double) as valor,
                     CAST(count(t) as int) as quantidade
             from TransacaoEntity t
             where t.usuarioId = :usuarioId
             and t.status = br.com.ntt.common.transacao.domain.model.StatusTransacao.AUTORIZADO
             group by t.tipo,  DATE(dataHoraSolicitacao)
        """)
    List<AnaliseDeDespesaCampos> visualisarGastosDia(@Param("usuarioId") UUID usuarioId);

    @Query(value = """
                 select
                       SUBSTRING(DATE_TRUNC('month', t.data_hora_solicitacao)::text from 1 for 7) as dataHoraSolicitacao,
                       t.tipo as tipo,
                       sum (t.valor) as valor,
                       count(*) as quantidade
    
                 from transacoes t
                 where t.usuario_id = ?1
                 and t.status = 1
                 group by t.tipo , DATE_TRUNC('month', t.data_hora_solicitacao)

            """, nativeQuery = true)
    List<AnaliseDeDespesaCampos> visualisarGastosMes(UUID usuarioId);

    List<TransacaoEntity> findAllByUsuarioId(UUID usuarioId);

}
