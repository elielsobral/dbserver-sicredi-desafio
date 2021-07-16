package br.com.sicredi.sessao.app.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.sicredi.sessao.app.dtos.SessaoDTO;
import br.com.sicredi.sessao.app.entities.enums.Status;
import br.com.sicredi.sessao.app.services.SessaoService;

@RestController
@RequestMapping(value = "/sessoes/v1")
public class SessaoController {

	@Autowired
	private SessaoService sessaoService;

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@RequestBody SessaoDTO objDto) {
		SessaoDTO objBody = sessaoService.insert(objDto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(objBody.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<SessaoDTO> findById(@PathVariable Long id) {
		SessaoDTO objDto = sessaoService.findById(id);
		return ResponseEntity.ok().body(objDto);
	}
	
	@RequestMapping(value = "/pauta/{id}", method = RequestMethod.GET)
	public ResponseEntity<SessaoDTO> findByPautaId(@PathVariable Long id) {
		SessaoDTO objDto = sessaoService.findByPautaId(id);
		return ResponseEntity.ok().body(objDto);
	}
	
	@GetMapping(value = "/pauta/{id}/duracao/{duracao}")
	public ResponseEntity<SessaoDTO> abrirSessaoParaVotacao(@PathVariable Long id, @PathVariable Long duracao) {
		SessaoDTO sessao = sessaoService.abrirSessaoDeVoto(id, duracao);
		return ResponseEntity.ok(sessao);
	}
	
	@GetMapping(value = "/status/{id}")
	public ResponseEntity<Status> consultaStatusDaSessaoDeVoto(@PathVariable Long id) {
		Status status = sessaoService.consultaStatusDaSessaoDeVoto(id);
		return ResponseEntity.ok(status);
	}
	
}
