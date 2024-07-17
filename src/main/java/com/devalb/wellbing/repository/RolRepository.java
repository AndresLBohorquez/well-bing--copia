package com.devalb.wellbing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devalb.wellbing.entities.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {

}
