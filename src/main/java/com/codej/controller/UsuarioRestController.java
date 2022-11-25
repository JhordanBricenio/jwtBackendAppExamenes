package com.codej.controller;
import com.codej.model.Usuario;
import com.codej.service.IUsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class UsuarioRestController {

    private final IUsuarioService usuarioService;

    @PostMapping("/usuarios")
    public Usuario saveUsuario(@RequestBody Usuario usuario){
        return usuarioService.save(usuario);
    }
    @GetMapping("/usuarios")
    public List<Usuario> getUsuarios(){
        return usuarioService.findAll();
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/usuarios/{id}")
    public String delete(@PathVariable Integer id){
        usuarioService.delete(id);
        return "Usuario eliminado";
    }


}
