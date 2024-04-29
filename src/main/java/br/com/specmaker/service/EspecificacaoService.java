package br.com.specmaker.service;


import br.com.specmaker.entity.Especificacao;
import br.com.specmaker.repository.EspecificacaoRepository;
import br.com.specmaker.utils.ByteArrayOutputStreamToFile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.io.ByteArrayOutputStream;
import java.io.File;

@Service
public class EspecificacaoService {

    @Autowired
    private AzureDevopsService azureDevopsService;

    @Autowired
    private AmazonS3Service s3Service;

    private static final Logger logger = LogManager.getLogger(EspecificacaoService.class);

    @Autowired
    private EspecificacaoRepository especificacaoRepository;


    public String gerarArquivoEspecificacao(final Especificacao especificacao) {
        try {
            UUID uuid = UUID.randomUUID();
            String nomeProjeto = especificacao.getProjeto().getNomeProjeto();
            String nomeArquivo = especificacao.getTitulo().concat( uuid.toString() ).concat(".docx");
            ByteArrayOutputStream stream = azureDevopsService.gerarArquivoWordEspecificacao(nomeProjeto, especificacao.getQueryId());
            File arquivoEspecificacao = ByteArrayOutputStreamToFile.createFileBy(nomeArquivo, stream);

            String urlArquivo = s3Service.uploadRepositorioAndGetUrl(arquivoEspecificacao);
            arquivoEspecificacao.delete();
            especificacao.setTitulo(nomeArquivo);

            return urlArquivo;

        } catch (Exception e) {
            logger.error(e);
        }

        return null;

    }

    public List<Especificacao> listarEspecificacoesPorProjeto(Long projetoId) {
        return especificacaoRepository.findByProjetoId(projetoId);
    }

    public void excluir(final Especificacao especificacao) {
        s3Service.deleteFile(especificacao.getTitulo());
        especificacaoRepository.delete(especificacao);
    }


}
