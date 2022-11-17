package br.com.specmaker.apiclient;

import br.com.specmaker.apiclient.records.QueryRecord;

public interface RestQueryClient {

    QueryRecord getQueryById(String projectName, String id);
}
