package br.com.sicredi.pauta.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.sicredi.pauta.app.entities.Pauta;

@Repository
public interface PautaRepository extends JpaRepository<Pauta, Long> {

}