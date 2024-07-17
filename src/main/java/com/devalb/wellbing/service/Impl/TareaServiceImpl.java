package com.devalb.wellbing.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devalb.wellbing.entities.Tarea;
import com.devalb.wellbing.repository.TareaRepository;
import com.devalb.wellbing.service.TareaService;

@Service
public class TareaServiceImpl implements TareaService {

    @Autowired
    private TareaRepository tareaRepository;

    @Override
    public List<Tarea> getTareas() {
        return tareaRepository.findAll();
    }

    @Override
    public Tarea getTareaById(Long id) {
        return tareaRepository.findById(id).orElse(null);
    }

    @Override
    public Tarea addTarea(Tarea tarea) {
        return tareaRepository.save(tarea);
    }

    @Override
    public Tarea editTarea(Tarea tarea) {
        return tareaRepository.save(tarea);
    }

    @Override
    public void deleteTarea(Long id) {
        Tarea tarea = tareaRepository.findById(id).orElse(null);
        if (tarea != null) {
            tarea.setVisible(false);
            tareaRepository.save(tarea);
        }
    }

    @Override
    public List<Tarea> getTareasByIdUsuario(Long id) {
        return tareaRepository.findAllByIdUsuario(id);
    }

    @Override
    public List<Tarea> getTareasByNuevaAndIdUsuario(Long estado, Long id) {
        return tareaRepository.findAllTareasByNuevasAndIdUsuario(estado, id);
    }

    @Override
    public List<Tarea> getTareasVencidas() {
        return tareaRepository.findAllTareasVencidas();
    }

}
