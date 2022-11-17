package br.com.specmaker.apiclient;

import br.com.specmaker.apiclient.records.QueryWorkItemRecord;
import br.com.specmaker.apiclient.records.WorkItemRecord;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public interface RestWorkItemClient {

    QueryWorkItemRecord listWorkItemByQueryId(String projectName, String queryId);

    WorkItemRecord getWorkItemById(String projectName, Long workItemId);

    byte[] getImageByUrl(final String imageFullUrl)
            throws ExecutionException, InterruptedException, IOException;

}
