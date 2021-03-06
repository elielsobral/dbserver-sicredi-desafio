package br.com.sicredi.sessao.app.services;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.sicredi.sessao.app.dtos.PautaDTO;
import br.com.sicredi.sessao.app.dtos.SessaoDTO;
import br.com.sicredi.sessao.app.entities.Sessao;
import br.com.sicredi.sessao.app.entities.enums.Status;
import br.com.sicredi.sessao.app.exceptions.ObjetoNaoEncontradoException;
import br.com.sicredi.sessao.app.repositories.SessaoRepository;
import br.com.sicredi.sessao.infra.proxy.feignclients.PautaFeignClient;

@Service
public class SessaoService {
	
	@Value("${sessao.duracao.default}")
	private Long DURACAO_SESSAO_DEFAULT;

	@Autowired
	private SessaoRepository sessaoRepository;
	
	@Autowired
	private PautaFeignClient pautaFeignClient;

	public SessaoDTO insert(SessaoDTO objDto) {
		Optional<PautaDTO> pautaDto = Optional.of(pautaFeignClient.findById(objDto.getPautaId()).getBody());
		pautaDto.orElseThrow(() -> new ObjetoNaoEncontradoException("Objeto não encontrado! Id: " + objDto.getPautaId() + ", Tipo: " + PautaDTO.class.getName()));
		Sessao obj = new Sessao(null, Instant.now(), DURACAO_SESSAO_DEFAULT, objDto.getPautaId(), Status.FECHADA);
		obj = sessaoRepository.save(obj);
		objDto.setId(obj.getId());
		return objDto;
	}

	public SessaoDTO findById(Long id) {
		Optional<Sessao> obj = sessaoRepository.findById(id);
		obj.orElseThrow(() -> new ObjetoNaoEncontradoException("Objeto não encontrado! Id: " + id + ", Tipo: " + Sessao.class.getName()));
		SessaoDTO objDto = new SessaoDTO(obj.get().getId(), obj.get().getInicio(), obj.get().getDuracao(), obj.get().getPautaId(), obj.get().getStatus());
		return objDto;
	}
	
	public SessaoDTO findByPautaId(Long id) {
		Optional<Sessao> obj = sessaoRepository.findByPautaId(id);
		obj.orElseThrow(() -> new ObjetoNaoEncontradoException("Objeto não encontrado! PautaId: " + id + ", Tipo: " + Sessao.class.getName()));
		SessaoDTO objDto = new SessaoDTO(obj.get().getId(), obj.get().getInicio(), obj.get().getDuracao(), obj.get().getPautaId(), obj.get().getStatus());
		return objDto;
	}

	public SessaoDTO abrirSessaoDeVoto(Long pautaId, Long duracao) {
		SessaoDTO objDto = this.findByPautaId(pautaId);
		Sessao obj = new Sessao(objDto.getId(), Instant.now(), ((null == duracao || duracao == 0) ?DURACAO_SESSAO_DEFAULT :duracao), objDto.getPautaId(), Status.ABERTA);
		obj = sessaoRepository.save(obj);
		return null;
	}
	
	public Status consultaStatusDaSessaoDeVoto(Long id) {
		SessaoDTO sessaoDto = this.findById(id);
		if(sessaoDto.getStatus().getCod() == Status.ABERTA.getCod()) {
			Duration tempoDecorrido = Duration.between(sessaoDto.getInicio(), Instant.now());
			boolean acabouTempoSessao = (tempoDecorrido.getSeconds() > sessaoDto.getDuracao());
			if (acabouTempoSessao) {
				sessaoDto.setStatus(Status.FECHADA);
				this.fecharSessaoDeVoto(sessaoDto.getId());
			}
		}
		return sessaoDto.getStatus();
	}
	
	private void fecharSessaoDeVoto(Long id) {
		SessaoDTO objDto = this.findById(id);
		Sessao obj = new Sessao(objDto.getId(), objDto.getInicio(), objDto.getDuracao(), objDto.getPautaId(), Status.FECHADA);
		obj = sessaoRepository.save(obj);
	}

}
