package org.example.springjdbcauth.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class Dao {
    @Autowired
    JdbcTemplate template;

    public String getPasswordByUsername(String username){
        String uri = "SELECT password FROM users WHERE username=?";
        return template.queryForObject(uri,new Object[]{username}, String.class);
    }
    public void newUser(String username, String password){
        String uri = "INSERT INTO users(username,password) VALUES (?,?)";
        template.update(uri,new Object[]{username,password});
    }
    public boolean UsernameIsExist(String username) {
        try{
            String uri = "SELECT count(*) FROM users WHERE username = ?";
            if (template.queryForObject(uri,new Object[]{username}, Integer.class)==1) return true;
            return false;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }
    public UserClass getUserByUsername(String name){
        if(!UsernameIsExist(name)) return null;
        String uri = "SELECT username,password,role FROM users WHERE username = ?";
        return template.queryForObject(uri,new Object[]{name},(rs,rowNum)-> {
            UserClass user = new UserClass(rs.getString("username"), rs.getString("password"),rs.getString("role"));
            return user;
        });
    }
}
