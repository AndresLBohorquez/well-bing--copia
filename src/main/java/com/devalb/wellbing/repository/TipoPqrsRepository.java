package com.devalb.wellbing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devalb.wellbing.entities.TipoPqrs;

@Repository
public interface TipoPqrsRepository extends JpaRepository<TipoPqrs, Long> {

}
