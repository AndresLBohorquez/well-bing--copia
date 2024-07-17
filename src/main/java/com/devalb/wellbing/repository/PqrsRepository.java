package com.devalb.wellbing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.devalb.wellbing.entities.Pqrs;
import java.util.List;

@Repository
public interface PqrsRepository extends JpaRepository<Pqrs, Long> {

    @Query(value = "SELECT * FROM pqrs WHERE usuario_id = ?1", nativeQuery = true)
    public List<Pqrs> findByIdUsuario(Long id);

}
