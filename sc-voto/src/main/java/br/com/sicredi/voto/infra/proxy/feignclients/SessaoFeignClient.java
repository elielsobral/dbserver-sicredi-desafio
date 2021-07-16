package br.com.sicredi.voto.infra.proxy.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.sicredi.voto.app.dtos.SessaoDTO;
import br.com.sicredi.voto.app.entities.enums.Status;

@Component
@FeignClient(name = "sc-sessao", path = "/sessoes/v1")
public interface SessaoFeignClient {

	@RequestMapping(value = "/pauta/{id}", method = RequestMethod.GET)
	ResponseEntity<SessaoDTO> findByPautaId(@PathVariable Long id);

	@GetMapping(value = "/status/{id}")
	ResponseEntity<Status> consultaStatusDaSessaoDeVoto(@PathVariable Long id);

}
