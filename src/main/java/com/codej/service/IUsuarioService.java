package com.codej.service;

import com.codej.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService {

    public Optional<Usuario> findByEmail(String email);
    public List<Usuario> findAll();
    public Usuario findById(Integer id);
    public Usuario save (Usuario usuario);
    public void delete(Integer id);


}
