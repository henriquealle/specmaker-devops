package br.com.specmaker.azuredevops;

import br.com.specmaker.record.QueryWorkItemRecord;
import br.com.specmaker.record.WorkItemRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

@Component
public class AzureDevopsRestWorkItemClient {

    private final WebClient localApiClient;

    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(10);

    private static final String GET_WORK_ITEM_BY_QUERY_ID = "/_apis/wit/wiql/{queryId}";
    private static final String GET_WORK_ITEM_BY_ID = "/_apis/wit/workitems/{id}";

    @Value("${azure.api.apiVersion}")
    private String azureDevopsApiVersion;

    @Autowired
    public AzureDevopsRestWorkItemClient(WebClient localApiClient){
        this.localApiClient = localApiClient;
    }

    public QueryWorkItemRecord listWorkItemByQueryId(String queryId){
        return localApiClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(GET_WORK_ITEM_BY_QUERY_ID)
                        .queryParam("api-version", azureDevopsApiVersion)
                        .build(queryId))
                .retrieve()
                .bodyToMono(QueryWorkItemRecord.class)
                .block(REQUEST_TIMEOUT);
    }

    public WorkItemRecord getWorkItemById(Long workItemId){
        return localApiClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(GET_WORK_ITEM_BY_ID)
                        .queryParam("api-version", azureDevopsApiVersion)
                        .build(workItemId))
                .retrieve()
                .bodyToMono(WorkItemRecord.class)
                .block(REQUEST_TIMEOUT);
    }
}
