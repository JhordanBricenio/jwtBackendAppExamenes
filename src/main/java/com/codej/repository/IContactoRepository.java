package com.codej.repository;

import com.codej.model.Contacto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IContactoRepository extends JpaRepository<Contacto, Integer> {


}
