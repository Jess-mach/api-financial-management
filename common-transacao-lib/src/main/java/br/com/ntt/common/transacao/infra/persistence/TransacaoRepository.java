package br.com.ntt.common.transacao.infra.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface TransacaoRepository extends JpaRepository<TransacaoEntity, UUID> {

    @Query(value = """
                 select 
                         DATE(t.data_hora_solicitacao) as dataHoraSolicitacao,
                         t.tipo as tipo ,
                         sum (t.valor) as valor,
                         count(*) as quantidade
                 from transacao t
                 where t.usuario_id = ?1
                 and t.status = 1
                 group by t.tipo , DATE(data_hora_solicitacao)
        
            """, nativeQuery = true)
    List<AnaliseDeDespesaCampos> visualisarGastosDia(UUID usuarioId);

    @Query(value = """
                 select
                       SUBSTRING(DATE_TRUNC('month', t.data_hora_solicitacao)::text from 1 for 7) as dataHoraSolicitacao,
                       t.tipo as tipo,
                       sum (t.valor) as valor,
                       count(*) as quantidade
    
                 from transacao t
                 where t.usuario_id = ?1
                 and t.status = 1
                 group by t.tipo , DATE_TRUNC('month', t.data_hora_solicitacao)

            """, nativeQuery = true)
    List<AnaliseDeDespesaCampos> visualisarGastosMes(UUID usuarioId);


}
