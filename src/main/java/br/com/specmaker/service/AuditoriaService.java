package br.com.specmaker.service;

import br.com.specmaker.entity.Projeto;
import br.com.specmaker.utils.FileCreator;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class AuditoriaService {

    @Autowired
    private AmazonS3Service s3Service;

    @Value("${auditoria.folderName}")
    private String auditoriaFolderName;

    public void registrarAuditoria(Operacao operacao, Projeto projeto) {
        String dataHoraOperacao = LocalDateTime.now().format( DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss") );

        String logAuditoria = "Operacação: " + operacao.name();
        logAuditoria = logAuditoria.concat( " Data da operação: " + dataHoraOperacao);
        logAuditoria = logAuditoria.concat( " Dados: " );
        logAuditoria = logAuditoria.concat( projeto.toString() ) ;

        String fileName = getAuditoriaFileName(operacao, dataHoraOperacao);
        File arquivoAuditoria = FileCreator.createFileBy(fileName, logAuditoria);

        s3Service.uploadFile(auditoriaFolderName, arquivoAuditoria);
        arquivoAuditoria.delete();
    }

    private String getAuditoriaFileName(Operacao operacao, String dataHoraOperacao) {
        UUID uuid = UUID.randomUUID();
        return operacao.name().concat("-").concat(dataHoraOperacao).concat(uuid.toString() +".log");
    }

    public enum Operacao {
        CADASTRO,
        ATUALIZACAO,
        EXCLUSAO
    }
}
