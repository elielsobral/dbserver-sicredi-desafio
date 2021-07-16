package br.com.sicredi.voto.app.entities;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.sicredi.voto.app.entities.enums.Opcao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TB_VOTO")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Voto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Instant efetivadoEm;
	private String cpfAssociado;
	private Long pautaId;
	private Opcao opcaoEscolhida;

}
