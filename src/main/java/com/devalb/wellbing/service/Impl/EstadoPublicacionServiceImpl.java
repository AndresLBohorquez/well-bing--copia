package com.devalb.wellbing.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devalb.wellbing.entities.EstadoPublicacion;
import com.devalb.wellbing.repository.EstadoPublicacionRepositoy;
import com.devalb.wellbing.service.EstadoPublicacionService;

@Service
public class EstadoPublicacionServiceImpl implements EstadoPublicacionService {

    @Autowired
    private EstadoPublicacionRepositoy ePublicacionRepositoy;

    @Override
    public List<EstadoPublicacion> getEstadosPublicacion() {
        return ePublicacionRepositoy.findAll();
    }

    @Override
    public EstadoPublicacion getEstadoPublicacionById(Long id) {
        return ePublicacionRepositoy.findById(id).orElse(null);
    }

    @Override
    public EstadoPublicacion addEstadoPublicacion(EstadoPublicacion estadoPublicacion) {
        return ePublicacionRepositoy.save(estadoPublicacion);
    }

    @Override
    public EstadoPublicacion editEstadoPublicacion(EstadoPublicacion estadoPublicacion) {
        return ePublicacionRepositoy.save(estadoPublicacion);
    }

    @Override
    public void deleteEstadoPublicacion(Long id) {
        ePublicacionRepositoy.deleteById(id);
    }

    @Override
    public EstadoPublicacion getEstadoPublicacionByNombre(String nombre) {
        return ePublicacionRepositoy.findByNombre(nombre);
    }

}
