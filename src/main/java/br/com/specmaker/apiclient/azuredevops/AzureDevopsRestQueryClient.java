package br.com.specmaker.apiclient.azuredevops;

import br.com.specmaker.apiclient.RestQueryClient;
import br.com.specmaker.apiclient.records.QueryRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

@Component
public class AzureDevopsRestQueryClient implements RestQueryClient {

    private final WebClient localApiClient;

    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(10);

    private static final String GET_QUERY_BY_ID = "/{projectName}/_apis/wit/queries/{id}";

    @Value("${azure.api.apiVersion}")
    private String azureDevopsApiVersion;

    @Autowired
    public AzureDevopsRestQueryClient(WebClient localApiClient){
        this.localApiClient = localApiClient;
    }

    public QueryRecord getQueryById(String projectName, String id){
        return localApiClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(GET_QUERY_BY_ID)
                        .queryParam("api-version", azureDevopsApiVersion)
                        .queryParam("$depth", 2)
                        .build(projectName, id))
                .retrieve()
                .bodyToMono(QueryRecord.class)
                .block(REQUEST_TIMEOUT);
    }
}
