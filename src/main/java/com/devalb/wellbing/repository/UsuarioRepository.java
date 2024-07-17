package com.devalb.wellbing.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.devalb.wellbing.entities.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    public Usuario findByUsername(String username);

    public Usuario findByEmail(String email);

    public Usuario findByCodigoUsuario(String codigoUsuario);

    @Query(value = "SELECT * FROM usuario WHERE enable = true", nativeQuery = true)
    public List<Usuario> findAllEnable();

    @Query(value = "SELECT * FROM usuario WHERE enable = true and tarea = false", nativeQuery = true)
    public List<Usuario> findAllEnableTarea();

    @SuppressWarnings("null")
    @Query(value = "SELECT * FROM usuario WHERE visible = true", nativeQuery = true)
    public List<Usuario> findAll();
}
