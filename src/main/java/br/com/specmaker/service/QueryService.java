package br.com.specmaker.service;

import br.com.specmaker.azuredevops.AzureDevopsRestQueryClient;
import br.com.specmaker.entity.Query;
import br.com.specmaker.entity.WorkItem;
import br.com.specmaker.word.WordDocCreator;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class QueryService {

    @Autowired
    private AzureDevopsRestQueryClient azureDevopsRestQueryClient;

    @Autowired
    private WorkItemService workItemService;

    @Autowired
    private WordDocCreator docCreator;



    public List<Query> listarQueries(){
        return new ArrayList<Query>();
    }

    public Query getQueryByID(String id){
        return new Query( azureDevopsRestQueryClient.getQueryById(id) );
    }


    public void gerarDocumento(String queryId)
            throws Exception {

        Query devopQuery = getQueryByID(queryId);

        if( !Objects.isNull(devopQuery) && !devopQuery.isFolder() ){
            devopQuery.setWorkItems( workItemService.listByQueryID(queryId) );
            docCreator.gerarArquivo( devopQuery );
        } else {
            throw new Exception("A query informada é uma pasta ou não existe");
        }

    }
}
