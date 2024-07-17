package com.devalb.wellbing.service;

import java.util.List;

import com.devalb.wellbing.entities.EstadoActivacion;

public interface EstadoActivacionService {

    public List<EstadoActivacion> getEstadosActivacion();

    public EstadoActivacion getEstadoActivacionById(Long id);

    public EstadoActivacion addEstadoActivacion(EstadoActivacion estadoActivacion);

    public EstadoActivacion editEstadoActivacion(EstadoActivacion estadoActivacion);

    public void deleteEstadoActivacion(Long id);
}
