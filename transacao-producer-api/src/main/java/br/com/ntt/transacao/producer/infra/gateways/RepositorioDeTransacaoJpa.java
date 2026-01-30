package br.com.ntt.transacao.producer.infra.gateways;

import br.com.ntt.common.transacao.domain.entity.RegistroDespesa;
import br.com.ntt.common.transacao.domain.entity.Transacao;
import br.com.ntt.common.transacao.infra.gateways.TransacaoEntityMapper;
import br.com.ntt.common.transacao.infra.persistence.AnaliseDeDespesaCampos;
import br.com.ntt.common.transacao.infra.persistence.TransacaoEntity;
import br.com.ntt.common.transacao.infra.persistence.TransacaoRepository;
import br.com.ntt.transacao.producer.application.gateways.RepositorioConsultaUsuario;
import br.com.ntt.transacao.producer.application.gateways.RepositorioDeTransacao;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Component
public class RepositorioDeTransacaoJpa implements RepositorioDeTransacao {

    private final TransacaoRepository repositorio;
    private final TransacaoEntityMapper mapper;

    public RepositorioDeTransacaoJpa(TransacaoRepository repositorio, TransacaoEntityMapper mapper) {
        this.repositorio = repositorio;
        this.mapper = mapper;
    }

    @Override
    public Transacao cadastrarTransacao(Transacao transacao, String token) {
        TransacaoEntity entity = mapper.toEntity(transacao);

        repositorio.save(entity);

        log.info("transacao salva no banco de dados");

        return mapper.toDomain(entity);
    }

    @Override
    public List<Transacao> listarTodos(UUID usuarioId) {

        List<TransacaoEntity> all;

        if (usuarioId != null) {
            log.info("listagem de transações - usuarioId={}", usuarioId);

            all = repositorio.findAllByUsuarioId(usuarioId);
        }
        else {
            log.info("listagem de transações sem usuarioId");

            all = repositorio.findAll();
        }

        return all.stream()
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
    public List<RegistroDespesa> visualizarGastosMes(UUID usuarioId) {
        List<AnaliseDeDespesaCampos> despesasPorMes = repositorio.visualisarGastosMes(usuarioId);

        return despesasPorMes.stream()
                .map(campos -> mapper.toDomain(campos))
                .collect(Collectors.toList());
    }
}
