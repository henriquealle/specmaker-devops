package br.com.specmaker.service;

import br.com.specmaker.azuredevops.RestQueryClient;
import br.com.specmaker.entity.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QueryService {

    @Value("${azure.api.mainFolderId}")
    private String mainFolder;

    @Autowired
    private RestQueryClient restQueryClient;

    public List<Query> listarQueries(){
        return new ArrayList<Query>();
    }

    public String getQueryByID(String id){
        return restQueryClient.getQueryById(id);
    }
}
