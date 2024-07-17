package com.devalb.wellbing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.devalb.wellbing.entities.EstadoEquipo;

@Repository
public interface EstadoEquipoRepositoy extends JpaRepository<EstadoEquipo, Long> {

    @Query(value = "SELECT * FROM estado_equipo WHERE nombre = ?1", nativeQuery = true)
    public EstadoEquipo findByNombreEstado(String nombre);
}
