package br.com.specmaker.azuredevops;

import br.com.specmaker.record.QueryWorkItemRecord;
import br.com.specmaker.record.WorkItemRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.ExecutionException;

@Component
public class AzureDevopsRestWorkItemClient {

    private final WebClient devopsRestApiClient;
    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(10);
    private static final String GET_WORK_ITEM_BY_QUERY_ID = "/{projectName}/_apis/wit/wiql/{queryId}";
    private static final String GET_WORK_ITEM_BY_WIT_ID = "/{projectName}/_apis/wit/workitems/{id}";

    @Value("${azure.api.apiVersion}")
    private String azureDevopsApiVersion;

    @Autowired
    public AzureDevopsRestWorkItemClient(WebClient devopsRestApiClient){
        this.devopsRestApiClient = devopsRestApiClient;
    }

    public QueryWorkItemRecord listWorkItemByQueryId(String projectName, String queryId){
        return devopsRestApiClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(GET_WORK_ITEM_BY_QUERY_ID)
                        .queryParam("api-version", azureDevopsApiVersion)
                        .build(projectName, queryId))
                .retrieve()
                .bodyToMono(QueryWorkItemRecord.class)
                .block(REQUEST_TIMEOUT);
    }

    public WorkItemRecord getWorkItemById(String projectName, Long workItemId){
        return devopsRestApiClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(GET_WORK_ITEM_BY_WIT_ID)
                        .queryParam("api-version", azureDevopsApiVersion)
                        .build(projectName, workItemId))
                .retrieve()
                .bodyToMono(WorkItemRecord.class)
                .block(REQUEST_TIMEOUT);
    }

    public byte[] getImageByUrl(final String imageFullUrl)
            throws ExecutionException, InterruptedException, IOException {

        return devopsRestApiClient
                .mutate()
                .baseUrl(imageFullUrl)
                .build()
                .get()
                .accept(MediaType.IMAGE_PNG)
                .retrieve()
                .bodyToMono(byte[].class)
                .block();

    }

}
