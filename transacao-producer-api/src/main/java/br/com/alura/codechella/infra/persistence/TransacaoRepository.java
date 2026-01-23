package br.com.alura.codechella.infra.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransacaoRepository extends JpaRepository<TransacaoEntity, Long> {
}
