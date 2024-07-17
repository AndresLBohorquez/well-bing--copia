package com.devalb.wellbing.service;

import java.util.List;

import com.devalb.wellbing.entities.EstadoPublicacion;

public interface EstadoPublicacionService {

    public List<EstadoPublicacion> getEstadosPublicacion();

    public EstadoPublicacion getEstadoPublicacionById(Long id);

    public EstadoPublicacion addEstadoPublicacion(EstadoPublicacion estadoPublicacion);

    public EstadoPublicacion editEstadoPublicacion(EstadoPublicacion estadoPublicacion);

    public void deleteEstadoPublicacion(Long id);

    public EstadoPublicacion getEstadoPublicacionByNombre(String nombre);
}
