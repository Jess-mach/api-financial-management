package br.com.ntt.transacao.producer.infra.gateways;

import br.com.ntt.transacao.producer.application.gateways.RepositorioDeTransacao;
import br.com.ntt.transacao.producer.domain.entities.transacao.analise.RegistroDespesa;
import br.com.ntt.transacao.producer.domain.entities.transacao.transacao.Transacao;
import br.com.ntt.transacao.producer.infra.persistence.AnaliseDeDespesaCampos;
import br.com.ntt.transacao.producer.infra.persistence.TransacaoEntity;
import br.com.ntt.transacao.producer.infra.persistence.TransacaoRepository;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class RepositorioDeTransacaoJpa implements RepositorioDeTransacao {

    private final TransacaoRepository repositorio;
    private final TransacaoEntityMapper mapper;

    public RepositorioDeTransacaoJpa(TransacaoRepository repositorio, TransacaoEntityMapper mapper) {
        this.repositorio = repositorio;
        this.mapper = mapper;
    }

    @Override
    public Transacao cadastrarTransacao(Transacao transacao) {
        TransacaoEntity entity = mapper.toEntity(transacao);
        repositorio.save(entity);
        return mapper.toDomain(entity);
    }

    @Override
    public List<Transacao> listarTodos() {
        return repositorio.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Transacao buscarPorId(UUID id) {
        TransacaoEntity entity = repositorio.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transação não encontrado"));

        return mapper.toDomain(entity);
    }

    @Override
    public List<RegistroDespesa> visualizarGastosDia(UUID usuarioId) {
        List<AnaliseDeDespesaCampos> despesasPorDia = repositorio.visualisarGastosDia(usuarioId);

        return despesasPorDia.stream()
                .map(campos -> mapper.toDomain(campos))
                .collect(Collectors.toList());
    }

    @Override
    public List<RegistroDespesa>  visualizarGastosMes(UUID usuarioId) {
        List<AnaliseDeDespesaCampos> despesasPorMes = repositorio.visualisarGastosMes(usuarioId);

        return despesasPorMes.stream()
                .map(campos -> mapper.toDomain(campos))
                .collect(Collectors.toList());
    }
}
