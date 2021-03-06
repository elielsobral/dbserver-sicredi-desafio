package br.com.sicredi.sessao.infra.config.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.sicredi.sessao.app.exceptions.ObjetoNaoEncontradoException;
import feign.FeignException;

@ControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler(ObjetoNaoEncontradoException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjetoNaoEncontradoException e, HttpServletRequest request) {
		StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(), "Não encontrado", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}
	
	@ExceptionHandler(FeignException.class)
	public ResponseEntity<StandardError> feignException(final FeignException e, HttpServletRequest request) {
		StandardError errorResponse = new StandardError(System.currentTimeMillis(), e.status(), "Erro interno de processamento - conversao retorno", e.getMessage(), request.getRequestURI()); 
		return new ResponseEntity<StandardError>(errorResponse, HttpStatus.valueOf(e.status()));
	}

}
