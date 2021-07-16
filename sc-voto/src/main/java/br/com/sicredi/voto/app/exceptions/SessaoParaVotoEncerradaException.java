package br.com.sicredi.voto.app.exceptions;

public class SessaoParaVotoEncerradaException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public SessaoParaVotoEncerradaException(String msg) {
		super(msg);
	}

	public SessaoParaVotoEncerradaException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
