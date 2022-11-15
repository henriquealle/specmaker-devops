package br.com.specmaker.azuredevops;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;


@Configuration
public class RestAzureDevopsWebClientFactory {

    private static final int MAX_MEMORY_SIZE = 16 * 1024 * 1024;

    @Value("${azure.api.baseUrl}")
    private String azureDevopsBaseUrl;

    @Value("${azure.api.token}")
    private String azureDevopsToken;

    @Value("${azure.api.repositorio}")
    private String azureDevopsRepo;

    @Value("${azure.api.apiVersion}")
    private String azureDevopsApiVersion;

    @Bean
    public WebClient localApiClient() {
        return WebClient.builder()
                .baseUrl( azureDevopsBaseUrl )
                .defaultHeaders((header) -> {
                    header.setBasicAuth("", azureDevopsToken);
                    header.setContentType(MediaType.valueOf("application/json-patch+json"));
                }).codecs(configurer -> {
                    configurer.defaultCodecs()
                            .maxInMemorySize(MAX_MEMORY_SIZE);
                })
                .build();
    }


}
