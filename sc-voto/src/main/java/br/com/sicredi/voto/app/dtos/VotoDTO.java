package br.com.sicredi.voto.app.dtos;

import java.io.Serializable;
import java.time.Instant;

import br.com.sicredi.voto.app.entities.enums.Opcao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VotoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private Instant efetivadoEm;
	private String cpfAssociado;
	private Long pautaId;
	private Opcao opcaoEscolhida;

}
