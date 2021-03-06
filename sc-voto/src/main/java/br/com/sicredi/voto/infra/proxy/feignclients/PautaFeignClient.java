package br.com.sicredi.voto.infra.proxy.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.sicredi.voto.app.dtos.PautaDTO;

@Component
@FeignClient(name = "sc-pauta", path = "/pautas/v1")
public interface PautaFeignClient {

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	ResponseEntity<PautaDTO> findById(@PathVariable Long id);
	
	@RequestMapping(value = "/atualiza", method = RequestMethod.PUT)
	public ResponseEntity<PautaDTO> update(@RequestBody PautaDTO objDto);

}
