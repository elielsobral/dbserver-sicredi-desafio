package br.com.sicredi.voto.app.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.sicredi.voto.app.entities.Voto;
import br.com.sicredi.voto.app.entities.enums.Opcao;

public interface VotoRepository extends JpaRepository<Voto, Long> {

	Optional<Voto> findByCpfAssociado(String cpfAssociado);
	
	long countByOpcaoEscolhidaAndPautaId(Opcao opcaoEscolhida, Long pautaId);

}
