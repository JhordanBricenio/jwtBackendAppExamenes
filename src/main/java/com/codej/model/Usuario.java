package com.codej.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Data
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private String apellidos;
    private String email;
    private String password;
    private String telefono;
    private Boolean enabled=true;
    private String foto;

    @JsonIgnoreProperties(value = {"usuarios", "hibernateLazyInitializer", "handler"})
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_rol", joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id"))

    private List<Rol> roles;

    public void agregar(Rol temRol) {
        if(roles==null) {
            roles= new LinkedList<Rol>();
        }
        roles.add(temRol);
    }


    public Usuario() {
        this.roles= new ArrayList<>();
    }

}
