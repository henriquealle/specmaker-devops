package br.com.specmaker.service;


import br.com.specmaker.entity.Especificacao;
import br.com.specmaker.utils.ByteArrayOutputStreamToFile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;
import java.io.ByteArrayOutputStream;
import java.io.File;

@Service
public class EspecificacaoService {

    @Autowired
    private QueryService queryService;

    @Autowired
    private AmazonS3Service s3Service;

    private static final Logger logger = LogManager.getLogger(EspecificacaoService.class);


    public String gerarArquivoEspecificacao(final Especificacao especificacao) {
        try {
            String nomeProjeto = especificacao.getProjeto().getNomeProjeto();
            UUID uuid = UUID.randomUUID();

            String nomeArquivo = especificacao.getTitulo().concat( uuid.toString() ).concat(".docx");
            ByteArrayOutputStream stream = queryService.gerarArquivoWordEspecificacao(nomeProjeto, especificacao.getQueryId());
            File arquivoEspecificacao = ByteArrayOutputStreamToFile.createFileBy(nomeArquivo, stream);

            return s3Service.uploadRepositorioAndGetUrl(arquivoEspecificacao);

        } catch (Exception e) {
            logger.error(e);
        }

        return null;

    }


}
