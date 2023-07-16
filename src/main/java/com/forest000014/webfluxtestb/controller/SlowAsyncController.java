package com.forest000014.webfluxtestb.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
public class SlowAsyncController {

    private final String SLOW_SERVER_URL = "http://localhost:8081/slow?name=david";
    private final long SLEEP_TIME_SECONDS = 5;

    @GetMapping("/slow")
    @ResponseBody
    public Mono<String> slowAsyncMethod() {
        System.out.println("====== /slow requested !! ======");

        Mono<String> bodyMono = WebClient.create(SLOW_SERVER_URL)
                .get()
                .retrieve()
                .bodyToMono(String.class);

        return bodyMono;
    }

    @GetMapping("/sleep")
    @ResponseBody
    public Mono<String> sleepMethod() throws InterruptedException {
        System.out.println("====== /sleep requested !! ======");

        for (int i = 1; i <= SLEEP_TIME_SECONDS; i++) {
            Thread.sleep(1000);
            System.out.printf("sleeping for %d second(s)...\n", i);
        }

        return Mono.just("Slept for 5 seconds.");
    }
}