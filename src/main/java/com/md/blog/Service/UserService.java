package com.md.blog.Service;

import com.md.blog.Model.User;
import com.md.blog.Repo.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
    public User addUser(User user){
        userRepository.save(user);
        return user;
    }
}
