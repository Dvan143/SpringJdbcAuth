package org.example.springjdbcauth.db;

public class UserClass {
    private int id;
    private String username;
    private String password;
    private String role;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    public String getRole(){
        return role;
    }
    public UserClass(String username, String password, String role){
        this.username = username;
        this.password = password;
        this.role = role;
    }
}