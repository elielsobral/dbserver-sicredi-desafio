package br.com.sicredi.voto.app.exceptions;

public class SessaoParaVotoEmAndamentoException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public SessaoParaVotoEmAndamentoException(String msg) {
		super(msg);
	}

	public SessaoParaVotoEmAndamentoException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
