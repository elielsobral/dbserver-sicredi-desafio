package br.com.sicredi.voto.infra.proxy.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.sicredi.voto.app.dtos.UserInfo;

@Component
@FeignClient(name = "user-info", url = "https://user-info.herokuapp.com", path = "/users")
public interface UserInfoFeignClient {

	@RequestMapping(value = "/{cpf}", method = RequestMethod.GET)
	UserInfo autorizaCpf(@PathVariable String cpf);

}
