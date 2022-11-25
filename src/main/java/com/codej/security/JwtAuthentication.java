package com.codej.security;

import com.codej.excepciones.UsuarioNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;


public class JwtAuthentication extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        AuthCredentials authCredentials = new AuthCredentials();


        try {
            authCredentials= new ObjectMapper().readValue(request.getReader(), AuthCredentials.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                authCredentials.getEmail(),
                authCredentials.getPassword(),
                Collections.emptyList()

        );
        return getAuthenticationManager().authenticate(authenticationToken);

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult)
            throws IOException, ServletException {

        User user = (User) authResult.getPrincipal();
        TokenUtils tokenUtils = new TokenUtils();

        String token = null;
        try {
            token = tokenUtils.createToken(user);
        } catch (UsuarioNotFoundException e) {
            throw new RuntimeException(e);
        }
        response.addHeader("Authorization", "Bearer " + token);
        response.getWriter().flush();

        super.successfulAuthentication(request, response, chain, authResult);
    }
}
