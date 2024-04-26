package br.com.specmaker.service;

import br.com.specmaker.apiclient.RestQueryClient;
import br.com.specmaker.entity.Query;
import br.com.specmaker.word.EspecificacaoWordCreator;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class QueryService {

    @Autowired
    private RestQueryClient apiQueryClient;

    @Autowired
    private WorkItemService workItemService;

    @Autowired
    private EspecificacaoWordCreator docCreator;

    @Autowired
    private AmazonS3Service amazonS3Service;



    public List<Query> listarQueries(){
        return new ArrayList<Query>();
    }

    public Query getQueryByID(String projectName, String id){
        return new Query( apiQueryClient.getQueryById( projectName, id ) );
    }

    public ByteArrayOutputStream obterArquivoEspecificacao(String projectName, String queryId)
            throws Exception {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        XWPFDocument documentStream = gerarDocumento(projectName, queryId);
        documentStream.write(stream);
        documentStream.close();
        return stream;
    }

    private XWPFDocument gerarDocumento(String projectName, String queryId)
            throws Exception {

        Query devopQuery = getQueryByID(projectName, queryId);
        XWPFDocument documentStream = null;

        if( !Objects.isNull(devopQuery) && !devopQuery.isFolder() ){
            devopQuery.setWorkItems( workItemService.listWorkItemBy(projectName, queryId) );
            documentStream = docCreator.gerarArquivoEspecificacao( devopQuery );
        } else {
            throw new Exception("A query informada é uma pasta ou não existe");
        }

        return documentStream;
    }
}
