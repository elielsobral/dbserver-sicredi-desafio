package br.com.sicredi.voto.app.dtos;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String status;

}
