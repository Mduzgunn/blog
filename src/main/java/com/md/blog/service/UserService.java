package com.md.blog.service;

import com.md.blog.model.User;
import com.md.blog.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

   protected User getUserById(String id){
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("user not found"));
   }

   protected List<User> getUserList(){
        return userRepository.findAll();
   }
}
