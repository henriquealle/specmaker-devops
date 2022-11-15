package br.com.specmaker.service;

import br.com.specmaker.azuredevops.AzureDevopsRestQueryClient;
import br.com.specmaker.entity.Query;
import br.com.specmaker.word.EspecificacaoWordCreator;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private EspecificacaoWordCreator docCreator;



    public List<Query> listarQueries(){
        return new ArrayList<Query>();
    }

    public Query getQueryByID(String id){
        return new Query( azureDevopsRestQueryClient.getQueryById(id) );
    }


    public XWPFDocument gerarDocumento(String queryId)
            throws Exception {

        Query devopQuery = getQueryByID(queryId);
        XWPFDocument documentStream = null;

        if( !Objects.isNull(devopQuery) && !devopQuery.isFolder() ){
            devopQuery.setWorkItems( workItemService.listWorkItemByQueryID(queryId) );
            documentStream = docCreator.gerarArquivoEspecificacao( devopQuery );
        } else {
            throw new Exception("A query informada é uma pasta ou não existe");
        }

        return documentStream;
    }
}
