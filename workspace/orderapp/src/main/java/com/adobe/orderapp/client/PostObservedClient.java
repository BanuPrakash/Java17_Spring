package com.adobe.orderapp.client;

import com.adobe.orderapp.service.PostService;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostObservedClient implements CommandLineRunner {
    private final PostService service;
    @Override
    public void run(String... args) throws Exception {
//        testMethod();
    }
    @Observed(name="posts.list", contextualName = "posts.get-Posts")
    private void testMethod() {
        service.getPosts();
        service.getUsers();
    }


}
