package com.codej.service;

import com.codej.model.Usuario;

import java.util.List;

public interface IUsuarioService {

    public List<Usuario> findAll();
    public Usuario findById(Integer id);
    public Usuario save (Usuario usuario);
    public void delete(Integer id);
}
