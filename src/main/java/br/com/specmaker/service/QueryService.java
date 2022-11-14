package br.com.specmaker.service;

import br.com.specmaker.azuredevops.AzureDevopsRestQueryClient;
import br.com.specmaker.entity.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QueryService {

    @Autowired
    private AzureDevopsRestQueryClient azureDevopsRestQueryClient;

    public List<Query> listarQueries(){
        return new ArrayList<Query>();
    }

    public Query getQueryByID(String id){
        return new Query( azureDevopsRestQueryClient.getQueryById(id) );
    }
}
