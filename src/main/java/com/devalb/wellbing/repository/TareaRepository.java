package com.devalb.wellbing.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.devalb.wellbing.entities.Tarea;

@Repository
public interface TareaRepository extends JpaRepository<Tarea, Long> {

    @Query(value = "SELECT * FROM tarea WHERE usuario_id = ?1 and visible = true", nativeQuery = true)
    public List<Tarea> findAllByIdUsuario(Long id);

    @Query(value = "SELECT * FROM tarea WHERE estado_tarea_id = ?1 and usuario_id =?2 and visible = true", nativeQuery = true)
    public List<Tarea> findAllTareasByNuevasAndIdUsuario(Long estado, Long id);

    @Query(value = "SELECT * FROM tarea WHERE MONTH(fecha) != MONTH(CURRENT_DATE)  and visible = true", nativeQuery = true)
    public List<Tarea> findAllTareasVencidas();

}
