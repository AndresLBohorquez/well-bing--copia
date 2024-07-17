package com.devalb.wellbing.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.devalb.wellbing.entities.RedesUsuario;

@Repository
public interface RedesUsuarioRepository extends JpaRepository<RedesUsuario, Long> {

    @Query(value = "SELECT * FROM redes_usuario where usuario_id = ?1", nativeQuery = true)
    public List<RedesUsuario> findAllByIdUsuario(Long id);
}
