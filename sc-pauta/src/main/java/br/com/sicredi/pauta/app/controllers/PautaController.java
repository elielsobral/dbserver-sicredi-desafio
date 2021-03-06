package br.com.sicredi.pauta.app.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.sicredi.pauta.app.dtos.PautaDTO;
import br.com.sicredi.pauta.app.services.PautaService;

@RestController
@RequestMapping(value = "/pautas/v1")
public class PautaController {

	@Autowired
	private PautaService pautaService;

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@RequestBody PautaDTO objDto) {
		PautaDTO objBody = pautaService.insert(objDto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(objBody.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<PautaDTO> findById(@PathVariable Long id) {
		PautaDTO objDto = pautaService.findById(id);
		return ResponseEntity.ok().body(objDto);
	}
	
	@RequestMapping(value = "/atualiza", method = RequestMethod.PUT)
	public ResponseEntity<PautaDTO> update(@RequestBody PautaDTO objDto) {
		PautaDTO pautaDto = pautaService.atualizaVotoPauta(objDto);
		return ResponseEntity.ok().body(pautaDto);
	}

}
