package br.com.specmaker.azuredevops;

import br.com.specmaker.record.QueryWorkItemRecord;
import br.com.specmaker.record.WorkItemRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.ExecutionException;

@Component
public class AzureDevopsRestWorkItemClient {

    private final WebClient localApiClient;

    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(10);

    private static final String GET_WORK_ITEM_BY_QUERY_ID = "/_apis/wit/wiql/{queryId}";
    private static final String GET_WORK_ITEM_BY_WIT_ID = "/_apis/wit/workitems/{id}";
    private static final String GET_WORK_ITEM_IMG_BY_WIT_ID = "/_apis/wit/attachments/{id}";

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
                        .path(GET_WORK_ITEM_BY_WIT_ID)
                        .queryParam("api-version", azureDevopsApiVersion)
                        .build(workItemId))
                .retrieve()
                .bodyToMono(WorkItemRecord.class)
                .block(REQUEST_TIMEOUT);
    }

    public Mono<Byte[]> retrieveImageFromWorkItemById(final Long workItemId)
            throws ExecutionException, InterruptedException, IOException {

        return localApiClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(GET_WORK_ITEM_IMG_BY_WIT_ID)
                        .queryParam("fileName", "image.png")
                        .build(workItemId))
                .accept(MediaType.IMAGE_PNG)
                .retrieve()
                .bodyToMono(Byte[].class);

    }

   /* public Mono<byte[]> retrieveImageFromWorkItemById(final Long workItemId)
            throws ExecutionException, InterruptedException, IOException {

        Mono<byte[]> image = localApiClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(GET_WORK_ITEM_IMG_BY_WIT_ID)
                        .queryParam("fileName", "image.png")
                        .build(workItemId))
                .retrieve()
                .bodyToMono();

        return image;
    }*/
}
