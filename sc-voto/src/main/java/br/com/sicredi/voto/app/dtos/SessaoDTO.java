package br.com.sicredi.voto.app.dtos;

import java.io.Serializable;
import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessaoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private Instant inicio;
	private Long duracao;
	private Long pautaId;

}
