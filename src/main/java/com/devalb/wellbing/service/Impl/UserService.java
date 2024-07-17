package com.devalb.wellbing.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.devalb.wellbing.entities.Rol;
import com.devalb.wellbing.entities.Usuario;
import com.devalb.wellbing.repository.UsuarioRepository;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {

            Usuario us = new Usuario();
            us = usuarioRepository.findByUsername(username);

            if (us == null) {
                us = usuarioRepository.findByEmail(username);
            }

            if (us.isEnable()) {
                List<GrantedAuthority> roles = new ArrayList<>();
                var rol = us.getRoles();
                for (Rol rol2 : rol) {
                    roles.add(new SimpleGrantedAuthority(rol2.getNombre()));
                }

                UserDetails userDetails = new User(us.getUsername(), us.getPassword(), roles);
                return userDetails;
            } else {
                UserDetails userDetails = new User(".", ".", new ArrayList<>());
                return userDetails;
            }

        } catch (Exception e) {
            UserDetails userDetails = new User(".", ".", new ArrayList<>());
            return userDetails;
        }

    }

}
