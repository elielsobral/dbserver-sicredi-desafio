package br.com.sicredi.associado.app.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.sicredi.associado.app.dtos.AssociadoDTO;
import br.com.sicredi.associado.app.services.AssociadoService;

@RestController
@RequestMapping(value = "/associados/v1")
public class AssociadoController {

	@Autowired
	private AssociadoService associadoService;

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@RequestBody AssociadoDTO objDto) {
		AssociadoDTO objBody = associadoService.insert(objDto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(objBody.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<AssociadoDTO> findById(@PathVariable Long id) {
		AssociadoDTO objDto = associadoService.findById(id);
		return ResponseEntity.ok().body(objDto);
	}
	
	@RequestMapping(value = "/cpf/{cpf}", method = RequestMethod.GET)
	public ResponseEntity<AssociadoDTO> findByCpf(@PathVariable String cpf) {
		AssociadoDTO objDto = associadoService.findByCpf(cpf);
		return ResponseEntity.ok().body(objDto);
	}

}
