package org.example.springjdbcauth.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.example.springjdbcauth.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControllerRest {
    @Autowired
    JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity login(HttpServletResponse response, @RequestParam(name = "username") String username, @RequestParam(name = "password") String password){
        Cookie cookie = new Cookie("jwt", jwtService.generateToken(username));
        cookie.setPath("/");
        cookie.setMaxAge(3600);
        cookie.setSecure(false);
        cookie.setHttpOnly(false);

        response.addCookie(cookie);

        System.out.println("Token from cookie: " + cookie.getValue());

        return new ResponseEntity<>("Logined", HttpStatus.OK);
    }
    @PostMapping("/register")
    public ResponseEntity register(@RequestParam(name = "username") String username, @RequestParam(name = "password") String password){
        return new ResponseEntity<>("", HttpStatus.OK);
    }
}
