package br.com.sicredi.sessao.app.entities.enums;

public enum Status {

	ABERTA(1, "Aberta"), 
	FECHADA(2, "Fechada");

	private int cod;
	private String descricao;

	private Status(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public int getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}

	public static Status toEnum(Integer cod) {

		if (null == cod) {
			return null;
		}

		for (Status x : Status.values()) {
			if (cod.equals(x.getCod())) {
				return x;
			}
		}

		throw new IllegalArgumentException("Id inválido: " + cod);

	}

}
