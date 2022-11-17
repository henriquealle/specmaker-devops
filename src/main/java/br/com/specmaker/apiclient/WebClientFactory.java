package br.com.specmaker.apiclient;

import org.springframework.web.reactive.function.client.WebClient;

public interface WebClientFactory {

    WebClient localApiClient();
}
