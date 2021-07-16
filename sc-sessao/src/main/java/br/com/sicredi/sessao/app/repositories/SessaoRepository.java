package br.com.sicredi.sessao.app.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.sicredi.sessao.app.entities.Sessao;

@Repository
public interface SessaoRepository extends JpaRepository<Sessao, Long> {

	Optional<Sessao> findByPautaId(Long id);

}