package br.com.ntt.transacao.producer.infra.gateways;

import br.com.ntt.common.transacao.domain.entity.RegistroDespesa;
import br.com.ntt.common.transacao.domain.entity.Transacao;
import br.com.ntt.common.transacao.infra.gateways.TransacaoEntityMapper;
import br.com.ntt.common.transacao.infra.persistence.AnaliseDeDespesaCampos;
import br.com.ntt.common.transacao.infra.persistence.TransacaoEntity;
import br.com.ntt.common.transacao.infra.persistence.TransacaoRepository;
import br.com.ntt.transacao.producer.application.gateways.RepositorioConsultaUsuario;
import br.com.ntt.transacao.producer.application.gateways.RepositorioDeTransacao;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class RepositorioDeTransacaoJpa implements RepositorioDeTransacao {

    private final TransacaoRepository repositorio;
    private final TransacaoEntityMapper mapper;
    private final RepositorioConsultaUsuario repositorioConsultaUsuario;

    public RepositorioDeTransacaoJpa(TransacaoRepository repositorio, TransacaoEntityMapper mapper, RepositorioConsultaUsuario repositorioConsultaUsuario) {
        this.repositorio = repositorio;
        this.mapper = mapper;
        this.repositorioConsultaUsuario = repositorioConsultaUsuario;
    }

    @Override
    public Transacao cadastrarTransacao(Transacao transacao, String token) {
        TransacaoEntity entity = mapper.toEntity(transacao);

        repositorioConsultaUsuario.buscarPorId(entity.getUsuarioId(), token);

        repositorio.save(entity);

        return mapper.toDomain(entity);
    }

    @Override
    public List<Transacao> listarTodos(UUID usuarioId) {
        List<TransacaoEntity> all = new ArrayList<>();

        if (usuarioId != null)
            all = repositorio.findAllByUsuarioId(usuarioId);
        else
            all = repositorio.findAll();

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
