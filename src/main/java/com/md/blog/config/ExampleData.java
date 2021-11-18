package com.md.blog.config;

import com.md.blog.model.Post;
import com.md.blog.model.PostTags;
import com.md.blog.repository.CommentRepository;
import com.md.blog.repository.PostRepository;
import com.md.blog.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.md.blog.model.User;

import java.util.Collections;

@Component
@ConditionalOnProperty(name = "command.line.runner.enable",havingValue = "true")
public class ExampleData implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public ExampleData(UserRepository userRepository, PostRepository postRepository, CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        /* This line will provide more reliable Data loading process on booting of application */
        if (!userRepository.findAll().isEmpty()) {
            return;
        }

    User user= new User(
            "username",
            "user@gmail.com"
    );
    userRepository.save(user);

    }
}
