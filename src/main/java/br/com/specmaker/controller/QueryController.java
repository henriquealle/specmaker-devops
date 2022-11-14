package br.com.specmaker.controller;

import br.com.specmaker.entity.Query;
import br.com.specmaker.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/queries")
public class QueryController {

    @Autowired
    private QueryService service;

    @Value("${azure.api.mainFolderId}")
    private String mainFolder;

    @GetMapping
    public List<Query> listQueries(){
        return service.listarQueries();
    }

    @GetMapping("/{id}")
    public String getMainQueryFolder(@PathVariable(value = "id", required = true) String id){
        return service.getQueryByID(id);
    }
}
