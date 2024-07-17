package com.devalb.wellbing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devalb.wellbing.entities.EstadoPublicacion;

@Repository
public interface EstadoPublicacionRepositoy extends JpaRepository<EstadoPublicacion, Long> {

    public EstadoPublicacion findByNombre(String nombre);
}
