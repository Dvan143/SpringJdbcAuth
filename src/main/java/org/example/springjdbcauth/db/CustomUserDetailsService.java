package org.example.springjdbcauth.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    Dao dao;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (dao.UsernameIsExist(username)){
            UserClass user = dao.getUserByUsername(username);
            return User.builder().username(user.getUsername()).password(user.getPassword()).roles(user.getRole()).build();
        } else{
            throw new UsernameNotFoundException("There is no user with given username");
        }
    }
}
