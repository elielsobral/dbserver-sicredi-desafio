package br.com.sicredi.voto.app.exceptions;

public class AssociadoNaoTemPermissaoVotarNestaPautaException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public AssociadoNaoTemPermissaoVotarNestaPautaException(String msg) {
		super(msg);
	}

	public AssociadoNaoTemPermissaoVotarNestaPautaException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
