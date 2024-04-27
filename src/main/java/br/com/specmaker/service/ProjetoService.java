package br.com.specmaker.service;


import br.com.specmaker.entity.Especificacao;
import br.com.specmaker.entity.Projeto;
import br.com.specmaker.exceptions.ProjetoNotFoundException;
import br.com.specmaker.repository.ProjetoRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjetoService {

    @Autowired
    private ProjetoRepository projetoRepository;

    @Autowired
    private EspecificacaoService especificacaoService;

    @Autowired
    private AuditoriaService auditoriaService;

    private static final Logger logger = LogManager.getLogger(ProjetoService.class);


    public List<Projeto> findAll() {
       return projetoRepository.findAll();
    }

    public List<Projeto> findAll(Pageable pageable) {
        return projetoRepository.findAll(pageable).toList();
    }

    public void cadastrar(Projeto projeto) {
        auditoriaService.registrarAuditoria(AuditoriaService.Operacao.CADASTRO, projeto);

        List<Especificacao> especs = projeto.getEspecificacoes();
        if (especs != null) especs.forEach( e -> {
            String urlArquivo = especificacaoService.gerarArquivoEspecificacao(e);
            e.setUrlArquivo(urlArquivo);
        });

        projetoRepository.save(projeto);
    }

    public void excluir(Long projetoId) {
        Projeto projeto = projetoRepository.findById(projetoId).orElseThrow(ProjetoNotFoundException::new);
        auditoriaService.registrarAuditoria(AuditoriaService.Operacao.EXCLUSAO, projeto);
        List<Especificacao> especs = especificacaoService.listarEspecificacoesPorProjeto(projetoId);
        if(especs != null) especs.forEach(e -> especificacaoService.excluir( e ) );
        projetoRepository.deleteById(projetoId);
    }

    public Projeto buscarPorId(Long id) {
        return projetoRepository.findById(id).orElseThrow(ProjetoNotFoundException::new);
    }

    public void alterar(final Projeto projeto) {
        auditoriaService.registrarAuditoria(AuditoriaService.Operacao.ATUALIZACAO, projeto);
        Projeto proj = projetoRepository.findById( projeto.getId() ).orElse(null);
        if( proj == null ) throw new ProjetoNotFoundException("Projeto não encontrado");
        projetoRepository.save(projeto);
    }

}
