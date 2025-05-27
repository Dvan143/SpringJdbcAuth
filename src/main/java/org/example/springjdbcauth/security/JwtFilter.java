package org.example.springjdbcauth.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.springjdbcauth.db.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    JwtService jwtService;
    CustomUserDetailsService customUserDetailsService;
    public JwtFilter(JwtService jwtService, CustomUserDetailsService customUserDetailsService){
        this.jwtService = jwtService;
        this.customUserDetailsService = customUserDetailsService;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        String token = null;

        if(cookies==null){
            filterChain.doFilter(request,response);
            return;
        }

        for(Cookie cookie : cookies){
            if (cookie.getName().equals("jwt")) token = cookie.getValue();
        }

        if(token==null){
            filterChain.doFilter(request,response);
            return;
        }

        if(jwtService.isTokenExpired(token)){
            Cookie cookie = new Cookie("jwt",null);
            response.addCookie(cookie);
            filterChain.doFilter(request,response);
            return;
        }

        String username = jwtService.getUsernameByToken(token);
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
        if(jwtService.verifyToken(token,username)){
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails
            ,null,userDetails.getAuthorities());
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
        filterChain.doFilter(request,response);
    }
}
