package com.devalb.wellbing.service;

import java.util.List;

import com.devalb.wellbing.entities.Usuario;

public interface UsuarioService {

    public List<Usuario> getUsuarios();

    public Usuario getUsuarioById(Long id);

    public Usuario addUsuario(Usuario usuario);

    public Usuario editUsuario(Usuario usuario);

    public void deleteUsuario(Long id);

    public Usuario getUsuarioByUsername(String username);

    public Usuario getUsuarioByEmail(String email);

    public Usuario getUsuarioByCodigUsuario(String codigoUsuario);

    public List<Usuario> getUsuariosActivos();
    
    public List<Usuario> getUsuariosActivosSinTarea();
}
