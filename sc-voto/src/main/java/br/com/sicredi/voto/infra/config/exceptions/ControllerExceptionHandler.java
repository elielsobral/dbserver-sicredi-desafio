package br.com.sicredi.voto.infra.config.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.sicredi.voto.app.exceptions.AssociadoJaVotouNestaPautaException;
import br.com.sicredi.voto.app.exceptions.AssociadoNaoTemPermissaoVotarNestaPautaException;
import br.com.sicredi.voto.app.exceptions.ObjetoNaoEncontradoException;
import br.com.sicredi.voto.app.exceptions.SessaoParaVotoEmAndamentoException;
import br.com.sicredi.voto.app.exceptions.SessaoParaVotoEncerradaException;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler(ObjetoNaoEncontradoException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjetoNaoEncontradoException e, HttpServletRequest request) {
		log.error("Não encontrado");
		StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(), "Não encontrado", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}
	
	@ExceptionHandler(AssociadoJaVotouNestaPautaException.class)
	public ResponseEntity<StandardError> associadoJaVotou(AssociadoJaVotouNestaPautaException e, HttpServletRequest request) {
		log.error("Associado ja votou");
		StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.UNAUTHORIZED.value(), "Associado ja votou", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(err);
	}
	
	@ExceptionHandler(AssociadoNaoTemPermissaoVotarNestaPautaException.class)
	public ResponseEntity<StandardError> associadoNaoTemPermissao(AssociadoNaoTemPermissaoVotarNestaPautaException e, HttpServletRequest request) {
		log.error("UNABLE_TO_VOTE: Voto nao permitido");
		StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.FORBIDDEN.value(), "UNABLE_TO_VOTE: Voto nao permitido", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
	}
	
	@ExceptionHandler(SessaoParaVotoEncerradaException.class)
	public ResponseEntity<StandardError> sessaoEncerrada(SessaoParaVotoEncerradaException e, HttpServletRequest request) {
		log.error("Sessão encerrada");
		StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.METHOD_NOT_ALLOWED.value(), "Sessão encerrada", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(err);
	}
	
	@ExceptionHandler(SessaoParaVotoEmAndamentoException.class)
	public ResponseEntity<StandardError> sessaoAberta(SessaoParaVotoEmAndamentoException e, HttpServletRequest request) {
		log.error("Sessão em andamento");
		StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.METHOD_NOT_ALLOWED.value(), "Sessão em andamento", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(err);
	}
	
	@ExceptionHandler(FeignException.class)
	public ResponseEntity<StandardError> feignException(final FeignException e, HttpServletRequest request) {
		log.error("Erro interno de processamento - conversao retorno");
		StandardError errorResponse = new StandardError(System.currentTimeMillis(), e.status(), "Erro interno de processamento - conversao retorno", e.getMessage(), request.getRequestURI());
		return new ResponseEntity<StandardError>(errorResponse, HttpStatus.valueOf(e.status()));
	}

}
