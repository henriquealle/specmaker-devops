package br.com.specmaker.azuredevops;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static java.time.Duration.*;


@Configuration
public class RestWebClient {

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
