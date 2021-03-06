package br.com.sicredi.pauta.app.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sicredi.pauta.app.dtos.PautaDTO;
import br.com.sicredi.pauta.app.entities.Pauta;
import br.com.sicredi.pauta.app.exceptions.ObjetoNaoEncontradoException;
import br.com.sicredi.pauta.app.repositories.PautaRepository;

@Service
public class PautaService {

	@Autowired
	private PautaRepository pautaRepository;

	public PautaDTO insert(PautaDTO objDto) {
		Pauta obj = new Pauta(null, objDto.getNome(), null, null);
		obj = pautaRepository.save(obj);
		objDto.setId(obj.getId());
		return objDto;
	}

	public PautaDTO findById(Long id) {
		Optional<Pauta> obj = pautaRepository.findById(id);
		obj.orElseThrow(() -> new ObjetoNaoEncontradoException("Objeto não encontrado! Id: " + id + ", Tipo: " + Pauta.class.getName()));
		PautaDTO objDto = new PautaDTO(obj.get().getId(), obj.get().getNome(), obj.get().getTotalSim(), obj.get().getTotalNao());
		return objDto;
	}
	
	public PautaDTO atualizaVotoPauta(PautaDTO objDto) {
		Pauta obj = new Pauta(objDto.getId(), objDto.getNome(), objDto.getTotalSim(), objDto.getTotalNao());
		obj = pautaRepository.save(obj);
		return objDto;
	}

}
