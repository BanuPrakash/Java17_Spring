package com.adobe.orderapp.cfg;

import com.adobe.orderapp.httpinterfaces.PostInterface;
import com.adobe.orderapp.httpinterfaces.UserInterface;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
@EnableAsync
@EnableScheduling
public class AppConfig {

    @Autowired
    CacheManager cacheManager;
    //    @Scheduled(fixedDelay = 1000)
    @Scheduled(cron = "0 0/30 * * * *")
    public void clearCache() {
        System.out.println("Cache Cleared!!!");
        cacheManager.getCacheNames().forEach(cache -> {
            cacheManager.getCache(cache).clear();
        });
    }
    @Bean
    RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean(name="asyncExecutors")
    public Executor asyncExecutor() {
//        Executors.newFixedThreadPool(10);
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3);
        executor.setMaxPoolSize(3);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("AsyncThread-");
        executor.initialize();
        return  executor;
    }

    // similar to RestTemplate
    @Bean
    WebClient webClient(ObjectMapper mapper) {
        return WebClient.builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .build();
    }

    @Bean
    PostInterface postClient(WebClient webClient) {
        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory
                .builderFor(WebClientAdapter.create(webClient))
                .build();
       return httpServiceProxyFactory.createClient(PostInterface.class);
    }

    @Bean
    UserInterface userClient(WebClient webClient) {
        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory
                .builderFor(WebClientAdapter.create(webClient))
                .build();
        return httpServiceProxyFactory.createClient(UserInterface.class);
    }
}
