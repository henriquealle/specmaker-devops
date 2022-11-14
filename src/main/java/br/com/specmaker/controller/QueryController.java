package br.com.specmaker.controller;

import br.com.specmaker.entity.Query;
import br.com.specmaker.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/gerar-documento/{queryId}")
    public void gerarDocumento(@PathVariable(value = "queryId", required = true) String queryId )
            throws Exception {
        queryService.gerarDocumento(queryId);
    }
}
