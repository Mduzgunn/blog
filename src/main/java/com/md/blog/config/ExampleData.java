//package com.md.blog.config;
//
//import com.md.blog.model.Post;
//import com.md.blog.model.PostTags;
//import com.md.blog.repository.CommentRepository;
//import com.md.blog.repository.PostRepository;
//import com.md.blog.repository.UserRepository;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.stereotype.Component;
//
//import com.md.blog.model.User;
//
//import java.util.Collections;
//
//@Component
//@ConditionalOnProperty(name = "command.line.runner.enable",havingValue = "true")
//public class ExampleData implements CommandLineRunner {
//    private final UserRepository userRepository;
//    private final PostRepository postRepository;
//    private final CommentRepository commentRepository;
//
//    public ExampleData(UserRepository userRepository, PostRepository postRepository, CommentRepository commentRepository) {
//        this.userRepository = userRepository;
//        this.postRepository = postRepository;
//        this.commentRepository = commentRepository;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//
//
//        User user1 = new User(
//                "ugurcandede",
//                "ugur@dede.com",
//                "Ugurcan Dede",
//                Collections.emptyList(),
//                Collections.emptyList());
//
//        Post post = new Post(
//                "Hello",
//                "Hello Folksie!~",
//                PostTags.CODE,
//                user1);
//
//
//    }
//}
