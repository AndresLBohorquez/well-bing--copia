package com.devalb.wellbing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devalb.wellbing.entities.Pago;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {

}
