package com.devalb.wellbing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devalb.wellbing.entities.EstadoActivacion;

@Repository
public interface EstadoActivacionRepository extends JpaRepository<EstadoActivacion, Long> {

}
