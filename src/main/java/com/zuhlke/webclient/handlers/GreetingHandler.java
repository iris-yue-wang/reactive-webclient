package com.zuhlke.webclient.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class GreetingHandler {

    private final WebClient client;

    @Autowired
    public GreetingHandler(WebClient client) {
        this.client = client;
    }

    public Mono<ServerResponse> greeting(ServerRequest request) {
        Mono<ClientResponse> result = client.get()
                .uri("/hello")
                .accept(MediaType.TEXT_PLAIN)
                .exchange();
        return result.log("GreetingHandler")
                .flatMap(clientResponse -> clientResponse.bodyToMono(String.class)
                        .flatMap(body -> ServerResponse.ok().body(BodyInserters.fromObject(body))));
    }
}
