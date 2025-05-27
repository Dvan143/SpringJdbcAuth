package org.example.springjdbcauth.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private UserService userService;
    public CustomUserDetailsService(UserService userService){
        this.userService = userService;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (userService.existsByUsername(username)){
             Optional<UserClass> user = userService.getUserByUsername(username);
            return User.builder().username(user.get().getUsername()).password(user.get().getPassword()).roles(user.get().getRole()).build();
        } else{
            throw new UsernameNotFoundException("There is no user with given username " + username);
        }
    }
}
