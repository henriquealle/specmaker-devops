package br.com.specmaker.azuredevops;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;


@Configuration
public class RestWebClientFactory {

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
                .baseUrl( createFullBaseUrl() )
                .defaultHeaders((header) -> {
                    header.setBasicAuth("", azureDevopsToken);
                    header.setContentType(MediaType.valueOf("application/json-patch+json"));
                })
                .build();
    }

    private String createFullBaseUrl(){
        return azureDevopsBaseUrl.concat("/").concat(azureDevopsRepo);
    }

}
