package com.adobe.orderapp.service;

import com.adobe.orderapp.dto.Post;
import com.adobe.orderapp.dto.User;
import com.adobe.orderapp.httpinterfaces.PostInterface;
import com.adobe.orderapp.httpinterfaces.UserInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostInterface postInterface; // proxy is wired --> AppConfig
    private final UserInterface userInterface;

    @Async("asyncExecutors")
    public CompletableFuture<List<Post>> getPosts() {
        System.out.println(Thread.currentThread());
        // Thread.sleep(3000);
        return  CompletableFuture.completedFuture(postInterface.getPosts());
    }

    @Async("asyncExecutors")
    public CompletableFuture<List<User>> getUsers() {
        System.out.println(Thread.currentThread());
        // Thread.sleep(3000);
        return  CompletableFuture.completedFuture(userInterface.getUsers());
    }
}
