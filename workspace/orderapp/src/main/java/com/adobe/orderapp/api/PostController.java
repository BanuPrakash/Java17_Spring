package com.adobe.orderapp.api;

import com.adobe.orderapp.dto.Post;
import com.adobe.orderapp.dto.User;
import com.adobe.orderapp.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    record PostDTO(String title, String user) {}
    @GetMapping()
    public List<PostDTO> getPosts() {
        CompletableFuture<List<Post>> posts = postService.getPosts(); // non-blocking
        CompletableFuture<List<User>> users = postService.getUsers(); // non-blocking

        List<Post> postlist = posts.join(); // blocking
        List<User> userlist = users.join(); // blocking

        return  postlist.stream().map(post -> {
            String username = userlist.stream()
                        .filter(user -> user.id() == post.userId())
                    .findFirst().get().name();
            return  new PostDTO(post.title(), username);
        }).collect(Collectors.toList());
    }
}
