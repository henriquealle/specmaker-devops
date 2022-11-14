package br.com.specmaker.controller;

import br.com.specmaker.entity.Query;
import br.com.specmaker.service.QueryService;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/queries")
public class QueryController {

    @Autowired
    private QueryService queryService;

    @Value("${azure.api.mainFolderId}")
    private String mainFolder;

    @GetMapping
    public List<Query> listQueries(){
        return queryService.listarQueries();
    }

    @GetMapping("/{id}")
    public Query getQueryByID(@PathVariable(value = "id", required = true) String id){
        return queryService.getQueryByID(id);
    }

    @GetMapping("/gerar-documento/{queryId}")
    public ResponseEntity<ByteArrayResource> gerarDocumento(
            @PathVariable(value = "queryId", required = true) String queryId )
            throws Exception {

        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            XWPFDocument documentStream = queryService.gerarDocumento(queryId);
            HttpHeaders header = new HttpHeaders();
            header.setContentType(new MediaType("application", "force-download"));
            header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=especificacao.docx");
            documentStream.write(stream);
            documentStream.close();
            return new ResponseEntity<>(new ByteArrayResource(stream.toByteArray()),
                    header, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
