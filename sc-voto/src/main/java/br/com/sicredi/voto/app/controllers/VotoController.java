package br.com.sicredi.voto.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.sicredi.voto.app.dtos.PautaDTO;
import br.com.sicredi.voto.app.dtos.VotoDTO;
import br.com.sicredi.voto.app.services.VotoService;

@RestController
@RequestMapping(value = "/votos/v1")
public class VotoController {

	@Autowired
	private VotoService votoService;

	@GetMapping(value = "/user/{cpf}/pauta/{pautaid}/voto/{opcao}")
	public ResponseEntity<VotoDTO> inserirVoto(@PathVariable String cpf, @PathVariable Long pautaid,	@PathVariable String opcao) {
		VotoDTO votoDto = votoService.inserirVoto(cpf, pautaid, opcao);
		return ResponseEntity.ok(votoDto);
	}
	
	@GetMapping(value = "/contabiliza/{pautaid}")
	public ResponseEntity<PautaDTO> contabilizaVoto(@PathVariable Long pautaid) {
		PautaDTO objDto = votoService.contabilizaVoto(pautaid);
		return ResponseEntity.ok(objDto);
	}
}
