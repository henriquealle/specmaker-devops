package br.com.specmaker.controller;

import br.com.specmaker.entity.Query;
import br.com.specmaker.service.QueryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.util.List;

@RestController
@RequestMapping("/queries")
public class QueryController {

    @Autowired
    private QueryService queryService;
    private static final Logger logger = LogManager.getLogger(QueryController.class);

    @GetMapping
    public List<Query> listQueries(){
        return queryService.listarQueries();
    }

    @GetMapping("/{projectName}/{id}")
    public Query getQueryByID(
            @PathVariable(value = "projectName", required = true) String projectName,
            @PathVariable(value = "id", required = true) String id){
        return queryService.getQueryByID(projectName, id);
    }

    @GetMapping("/gerar-documento/{projectName}/{queryId}")
    public ResponseEntity<ByteArrayResource> gerarDocumento(
            @PathVariable(value = "projectName", required = true) String projectName,
            @PathVariable(value = "queryId", required = true) String queryId )
            throws Exception {

        logger.info("gerando arquivo para a query: ".concat(queryId));
        return gerarArquivoEspecificacao(projectName, queryId);

    }

    private ResponseEntity<ByteArrayResource> gerarArquivoEspecificacao(String projectName, String queryId) throws Exception {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "force-download"));
        header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=especificacao.docx");

        ByteArrayResource arquivo = queryService.obterArquivoEspecificacao(projectName, queryId);
        return new ResponseEntity<>(arquivo, header, HttpStatus.CREATED);
    }
}
