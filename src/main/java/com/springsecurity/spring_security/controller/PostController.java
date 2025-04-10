package com.springsecurity.spring_security.controller;

import com.springsecurity.spring_security.models.Post;
import com.springsecurity.spring_security.respository.PostRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
public class PostController {

  PostRepository posts;

    public PostController(PostRepository posts) {
        this.posts = posts;
    }

    @GetMapping
    Iterable<Post> getAllPost(){
      return posts.findAll();
  }


  @GetMapping("/{postId}")
   Post getOnePost(@PathVariable("postId") Long id){
      return posts.findById(id).orElseThrow(()->new RuntimeException("Post is not found!"));
  }


}
