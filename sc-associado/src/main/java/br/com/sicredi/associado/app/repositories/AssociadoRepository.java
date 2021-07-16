package br.com.sicredi.associado.app.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.sicredi.associado.app.entities.Associado;

@Repository
public interface AssociadoRepository extends JpaRepository<Associado, Long> {

	Optional<Associado> findByCpf(String cpf);

}
