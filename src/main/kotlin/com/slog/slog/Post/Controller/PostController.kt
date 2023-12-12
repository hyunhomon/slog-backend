package com.slog.slog.Post.Controller

import com.slog.slog.Post.Model.PostModel
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PostController {
    @GetMapping("/api/post")
    fun post() : PostModel {
        var post : PostModel = PostModel()

        post.title = "asdf"
        post.thumbnail = "res/1.png"
        post.views = 12
        post.likes = 10
        post.dislikes = 1

        return post
    }
}