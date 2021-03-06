package br.com.sicredi.voto.app.entities.enums;

public enum Opcao {

	SIM(1, "Sim"), 
	NAO(2, "Não");

	private int cod;
	private String descricao;

	private Opcao(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public int getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}

	public static Opcao toEnum(Integer cod) {

		if (null == cod) {
			return null;
		}

		for (Opcao x : Opcao.values()) {
			if (cod.equals(x.getCod())) {
				return x;
			}
		}

		throw new IllegalArgumentException("Id inválido: " + cod);

	}

}
