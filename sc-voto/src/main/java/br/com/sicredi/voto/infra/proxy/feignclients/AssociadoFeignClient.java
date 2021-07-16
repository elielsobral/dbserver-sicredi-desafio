package br.com.sicredi.voto.infra.proxy.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.sicredi.voto.app.dtos.AssociadoDTO;

@Component
@FeignClient(name = "sc-associado", path = "/associados/v1")
public interface AssociadoFeignClient {

	@RequestMapping(value = "/cpf/{cpf}", method = RequestMethod.GET)
	public ResponseEntity<AssociadoDTO> findByCpf(@PathVariable String cpf);

}
