package com.devalb.wellbing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devalb.wellbing.entities.EstadoPqrs;

@Repository
public interface EstadoPqrsRepository extends JpaRepository<EstadoPqrs, Long> {

}
