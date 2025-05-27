package org.example.springjdbcauth.db;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    public boolean existsByUsername(String username){
        return userRepository.existsByUsername(username);
    }
    public Optional<UserClass> getUserByUsername(String username){
        return userRepository.getUserByUsername(username);
    }
    public void createNewUser(UserClass user){
        userRepository.save(user);
    }

    //Init

    @PostConstruct
    public void run(){
        userRepository.save(new UserClass("admin","admin","admin"));
        userRepository.save(new UserClass("bob","123124","user"));
    }
}
