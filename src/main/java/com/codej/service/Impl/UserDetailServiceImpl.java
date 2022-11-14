package com.codej.service.Impl;

import com.codej.model.Rol;
import com.codej.model.Usuario;
import com.codej.repository.IUsuarioRepository;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private Logger logger = LoggerFactory.getLogger(UserDetailServiceImpl.class);
    @Autowired
    private IUsuarioRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       Usuario usuario= userRepository.findOneByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Email"+email+" no encontrado"));

        List< GrantedAuthority> roles= usuario.getRoles().stream()
                .map(rol -> new SimpleGrantedAuthority(rol.getNombre()))
                .peek(rol -> logger.info("Rol: "+rol.getAuthority()))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);

        for (Rol rol: usuario.getRoles()){
            roles.add(new SimpleGrantedAuthority(rol.getNombre()));
}
       return new User(usuario.getEmail(), usuario.getPassword(), usuario.getEnabled(), true, true, true, roles);
    }

}
