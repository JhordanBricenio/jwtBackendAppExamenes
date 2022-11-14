package com.codej.controller;
import com.codej.model.Rol;
import com.codej.model.Usuario;
import com.codej.repository.IUsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("contactos")
@AllArgsConstructor
public class UsuarioRestController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    private final IUsuarioRepository usuarioRepository;

    @PostMapping
    @Secured("ROLE_ADMIN")
    public Usuario saveUsuario(@RequestBody Usuario usuario){
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        //Creamos el perfil que le asignaremos
        Rol rol= new Rol();
        rol.setId(3);//id usuario
        usuario.agregar(rol);


        return usuarioRepository.save(usuario);
    }
    @GetMapping
    @Secured("ROLE_ADMIN")
    public List<Usuario> getUsuarios(){
        return usuarioRepository.findAll();
    }


}
