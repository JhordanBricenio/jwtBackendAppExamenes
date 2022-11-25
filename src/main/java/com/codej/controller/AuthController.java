package com.codej.controller;

import com.codej.excepciones.UsuarioNotFoundException;
import com.codej.model.Usuario;
import com.codej.security.AuthCredentials;
import com.codej.security.JwtResponse;
import com.codej.security.TokenUtils;
import com.codej.service.IUsuarioService;
import com.codej.service.Impl.UserDetailServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin("*")
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailServiceImpl userDetailsService;

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private TokenUtils jwtUtils;

    @PostMapping("/generate-token")
    public ResponseEntity<?> generarToken(@RequestBody AuthCredentials jwtRequest) throws Exception {
        try{
            autenticar(jwtRequest.getEmail(),jwtRequest.getPassword());
        }catch (UsuarioNotFoundException exception){
            exception.printStackTrace();
            throw new Exception("Usuario no encontrado");
        }

        UserDetails userDetails =  this.userDetailsService.loadUserByUsername(jwtRequest.getEmail());
        String token = jwtUtils.createToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void autenticar(String username,String password) throws Exception {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
        }catch (DisabledException exception){
            throw  new Exception("USUARIO DESHABILITADO " + exception.getMessage());
        }catch (BadCredentialsException e){
            throw  new Exception("Credenciales invalidas " , e);
        }
    }

}
