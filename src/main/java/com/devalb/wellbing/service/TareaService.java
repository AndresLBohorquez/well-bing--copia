package com.devalb.wellbing.service;

import java.util.List;

import com.devalb.wellbing.entities.Tarea;

public interface TareaService {

    public List<Tarea> getTareas();

    public Tarea getTareaById(Long id);

    public Tarea addTarea(Tarea tarea);

    public Tarea editTarea(Tarea tarea);

    public void deleteTarea(Long id);

    public List<Tarea> getTareasByIdUsuario(Long id);

    public List<Tarea> getTareasByNuevaAndIdUsuario(Long estado, Long id);

    public List<Tarea> getTareasVencidas();
}
