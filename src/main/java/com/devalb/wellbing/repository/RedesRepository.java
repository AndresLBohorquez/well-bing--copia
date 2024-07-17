package com.devalb.wellbing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devalb.wellbing.entities.Redes;

@Repository
public interface RedesRepository extends JpaRepository<Redes, Long> {

}
