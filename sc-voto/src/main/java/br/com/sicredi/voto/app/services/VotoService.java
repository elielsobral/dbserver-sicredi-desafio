package br.com.sicredi.voto.app.services;

import java.time.Instant;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sicredi.voto.app.dtos.AssociadoDTO;
import br.com.sicredi.voto.app.dtos.PautaDTO;
import br.com.sicredi.voto.app.dtos.SessaoDTO;
import br.com.sicredi.voto.app.dtos.UserInfo;
import br.com.sicredi.voto.app.dtos.VotoDTO;
import br.com.sicredi.voto.app.entities.Voto;
import br.com.sicredi.voto.app.entities.enums.Opcao;
import br.com.sicredi.voto.app.entities.enums.Status;
import br.com.sicredi.voto.app.exceptions.AssociadoJaVotouNestaPautaException;
import br.com.sicredi.voto.app.exceptions.AssociadoNaoTemPermissaoVotarNestaPautaException;
import br.com.sicredi.voto.app.exceptions.ObjetoNaoEncontradoException;
import br.com.sicredi.voto.app.exceptions.SessaoParaVotoEmAndamentoException;
import br.com.sicredi.voto.app.exceptions.SessaoParaVotoEncerradaException;
import br.com.sicredi.voto.app.repositories.VotoRepository;
import br.com.sicredi.voto.infra.proxy.feignclients.AssociadoFeignClient;
import br.com.sicredi.voto.infra.proxy.feignclients.PautaFeignClient;
import br.com.sicredi.voto.infra.proxy.feignclients.SessaoFeignClient;
import br.com.sicredi.voto.infra.proxy.feignclients.UserInfoFeignClient;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class VotoService {
	
	@Autowired
	private UserInfoFeignClient userInfoFeignClient;
	
	@Autowired
	private AssociadoFeignClient associadoFeignClient;
	
	@Autowired
	private PautaFeignClient pautaFeignClient;

	@Autowired
	private SessaoFeignClient sessaoFeignClient;

	@Autowired
	private VotoRepository votoRepository;

	public VotoDTO inserirVoto(String cpfAssociado, Long pautaId, String opcaoEscolhida) {

		// - Validar se a pauta existe
		Optional<PautaDTO> pautaDto = Optional.of(pautaFeignClient.findById(pautaId).getBody());
		pautaDto.orElseThrow(() -> new ObjetoNaoEncontradoException("Objeto não encontrado! Id: " + pautaId + ", Tipo: " + PautaDTO.class.getName()));
		log.info("Acessando informações referente a PAUTA: " + pautaDto.toString());

		// - validar se a sessao existe
		Optional<SessaoDTO> sessaoDto = Optional.of(sessaoFeignClient.findByPautaId(pautaId).getBody());
		sessaoDto.orElseThrow(() -> new ObjetoNaoEncontradoException("Objeto não encontrado! Id: " + pautaId + ", Tipo: " + SessaoDTO.class.getName()));
		log.info("Acessando informações referente a SESSÃO: " + sessaoDto.toString());
		
		//- validar se sessao esta aberta para votar na pauta
		Status statusSessao = sessaoFeignClient.consultaStatusDaSessaoDeVoto(sessaoDto.get().getId()).getBody();
		if(statusSessao.getCod() == Status.FECHADA.getCod()) {
			throw new SessaoParaVotoEncerradaException("Sessão para votação encerrada! Pauta: " + sessaoDto.get().getPautaId());
		}
		log.info("Acessando informações referente ao STATUS da SESSÃO: " + statusSessao.toString());
		
		// - validar se o Associado existe
		Optional<AssociadoDTO> associadoDto = Optional.of(associadoFeignClient.findByCpf(cpfAssociado).getBody());
		associadoDto.orElseThrow(() -> new ObjetoNaoEncontradoException("Objeto não encontrado! CPF: " + cpfAssociado + ", Tipo: " + AssociadoDTO.class.getName()));
		log.info("Acessando informações referente a ASSOCIADO: " + associadoDto.toString());
		
		// - se o associado ja votou, NAO pode votar novamente nesta pauta
		Optional<Voto> voto = votoRepository.findByCpfAssociado(cpfAssociado);
		if(voto.isPresent()) {
			VotoDTO votoDto = new VotoDTO(voto.get().getId(), voto.get().getEfetivadoEm(), voto.get().getCpfAssociado(), voto.get().getPautaId(), voto.get().getOpcaoEscolhida());
			if(votoDto.getCpfAssociado().equals(cpfAssociado)) {
				throw new AssociadoJaVotouNestaPautaException("Associado ja votou nesta pauta! CPF: " + cpfAssociado + ", Tipo: " + AssociadoDTO.class.getName());
			}
		}
		
		// - validar se associado tem permissão para votar nesta pauta
		Voto entity = new Voto();
		Optional<UserInfo> userInfo = Optional.of(userInfoFeignClient.autorizaCpf(cpfAssociado));
		log.info("USER-INFO: " + userInfo.toString());
		
		if(userInfo.get().getStatus().toString() == "UNABLE_TO_VOTE") {
			throw new AssociadoNaoTemPermissaoVotarNestaPautaException("Voto nao permitido para este CPF! UserInfo: " + cpfAssociado + ", Tipo: " + AssociadoDTO.class.getName());
		} 
		
		//- Se chegou ate aqui... so registrar o Voto...
		entity = new Voto(null, Instant.now(), cpfAssociado, pautaId, Opcao.valueOf(opcaoEscolhida.toUpperCase()));
		entity = votoRepository.save(entity);
		
		VotoDTO votoDto = new VotoDTO(entity.getId(), entity.getEfetivadoEm(), entity.getCpfAssociado(), entity.getPautaId(), entity.getOpcaoEscolhida());
		log.info("VOTO INSERIDO: " + votoDto.toString());

		return votoDto;
	}
	
	public PautaDTO contabilizaVoto(Long pautaId) {
		
		// - Validar se a pauta existe
		Optional<PautaDTO> pautaDto = Optional.of(pautaFeignClient.findById(pautaId).getBody());
		pautaDto.orElseThrow(() -> new ObjetoNaoEncontradoException("Objeto não encontrado! Id: " + pautaId + ", Tipo: " + PautaDTO.class.getName()));
		log.info("Acessando informações referente a PAUTA: " + pautaDto.toString());
		
		// - validar se a sessao existe
		Optional<SessaoDTO> sessaoDto = Optional.of(sessaoFeignClient.findByPautaId(pautaId).getBody());
		sessaoDto.orElseThrow(() -> new ObjetoNaoEncontradoException("Objeto não encontrado! Id: " + pautaId + ", Tipo: " + SessaoDTO.class.getName()));
		log.info("Acessando informações referente a SESSÃO: " + sessaoDto.toString());
		
		//- validar se sessao esta aberta para votar na pauta
		Status statusSessao = sessaoFeignClient.consultaStatusDaSessaoDeVoto(sessaoDto.get().getId()).getBody();
		if(statusSessao.getCod() == Status.ABERTA.getCod()) {
			throw new SessaoParaVotoEmAndamentoException("Sessão para votação ainda esta ABERTA! Pauta: " + sessaoDto.get().getPautaId());
		}
		log.info("Acessando informações referente ao STATUS da SESSÃO: " + statusSessao.toString());
		

		Long votoSim = votoRepository.countByOpcaoEscolhidaAndPautaId(Opcao.SIM, pautaId);
		Long votoNao = votoRepository.countByOpcaoEscolhidaAndPautaId(Opcao.NAO, pautaId);
		
		PautaDTO obj = new PautaDTO(pautaDto.get().getId(), pautaDto.get().getNome(), votoSim, votoNao);
		pautaDto = Optional.of(pautaFeignClient.update(obj).getBody());
		
		return obj;
		
	}

}
