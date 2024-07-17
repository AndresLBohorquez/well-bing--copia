package com.devalb.wellbing.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devalb.wellbing.entities.Usuario;
import com.devalb.wellbing.repository.UsuarioRepository;
import com.devalb.wellbing.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<Usuario> getUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario getUsuarioById(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    @Override
    public Usuario addUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario editUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public void deleteUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario != null) {
            usuario.setVisible(false);
            usuario.setEnable(false);
            usuarioRepository.save(usuario);
        }
    }

    @Override
    public Usuario getUsuarioByUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    @Override
    public Usuario getUsuarioByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Override
    public List<Usuario> getUsuariosActivos() {
        return usuarioRepository.findAllEnable();
    }

    @Override
    public Usuario getUsuarioByCodigUsuario(String codigoUsuario) {
        return usuarioRepository.findByCodigoUsuario(codigoUsuario);
    }

    @Override
    public List<Usuario> getUsuariosActivosSinTarea() {
        return usuarioRepository.findAllEnableTarea();
    }

}
