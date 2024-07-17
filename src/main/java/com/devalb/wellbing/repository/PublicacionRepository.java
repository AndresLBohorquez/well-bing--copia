package com.devalb.wellbing.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.devalb.wellbing.entities.Publicacion;

@Repository
public interface PublicacionRepository extends JpaRepository<Publicacion, Long> {

    @Query(value = "SELECT * FROM publicacion WHERE usuario_id = ?1 and visible = true", nativeQuery = true)
    public List<Publicacion> findAllByIdUsuario(Long id);

    @Query(value = "SELECT * FROM publicacion WHERE visible = true", nativeQuery = true)
    public List<Publicacion> findAllVisible();

}
