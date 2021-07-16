package br.com.sicredi.associado.app.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sicredi.associado.app.dtos.AssociadoDTO;
import br.com.sicredi.associado.app.entities.Associado;
import br.com.sicredi.associado.app.exceptions.ObjetoNaoEncontradoException;
import br.com.sicredi.associado.app.repositories.AssociadoRepository;

@Service
public class AssociadoService {

	@Autowired
	private AssociadoRepository associadoRepository;

	public AssociadoDTO insert(AssociadoDTO objDto) {
		Associado obj = new Associado(null, objDto.getNome(), objDto.getCpf());
		obj = associadoRepository.save(obj);
		objDto.setId(obj.getId());
		return objDto;
	}

	public AssociadoDTO findById(Long id) {
		Optional<Associado> obj = associadoRepository.findById(id);
		obj.orElseThrow(() -> new ObjetoNaoEncontradoException("Objeto não encontrado! Id: " + id + ", Tipo: " + Associado.class.getName()));
		AssociadoDTO objDto = new AssociadoDTO(obj.get().getId(), obj.get().getNome(), obj.get().getCpf());
		return objDto;
	}
	
	public AssociadoDTO findByCpf(String cpf) {
		Optional<Associado> obj = associadoRepository.findByCpf(cpf);
		obj.orElseThrow(() -> new ObjetoNaoEncontradoException("Objeto não encontrado! CPF: " + cpf + ", Tipo: " + Associado.class.getName()));
		AssociadoDTO objDto = new AssociadoDTO(obj.get().getId(), obj.get().getNome(), obj.get().getCpf());
		return objDto;
	}

}
