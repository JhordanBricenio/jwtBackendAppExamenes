package com.codej.service;

import com.codej.model.Contacto;

import java.util.List;

public interface IContactoService {

    public List<Contacto> findAll();
    public Contacto findById(Integer id);
    public Contacto save (Contacto contacto);
    public void delete(Integer id);
}
