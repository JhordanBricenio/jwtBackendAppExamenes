package com.codej.service.Impl;

import com.codej.model.Rol;
import com.codej.model.Usuario;
import com.codej.repository.IUsuarioRepository;
import com.codej.service.IUsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UsuarioServiceImp implements IUsuarioService {

    private IUsuarioRepository usuarioRepository;

    private PasswordEncoder passwordEncoder;

    @Override
    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findOneByEmail(email);
    }

    @Override
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario findById(Integer id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    @Override
    public Usuario save(Usuario usuario) {
        usuario.setFoto("https://i.imgur.com/3ZQ3ZQx.png");
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        //Creamos el perfil que le asignaremos
        Rol rol= new Rol();
        rol.setId(3);//id usuario
        usuario.agregar(rol);
        return usuarioRepository.save(usuario);
    }

    @Override
    public void delete(Integer id) {
        usuarioRepository.deleteById(id);
    }
}
