package com.devalb.wellbing.service;

import java.util.List;

import com.devalb.wellbing.entities.EstadoEquipo;

public interface EstadoEquipoService {

    public List<EstadoEquipo> getEstadosEquipo();

    public EstadoEquipo getEstadoEquipoById(Long id);

    public EstadoEquipo addEstadoEquipo(EstadoEquipo estadoEquipo);

    public EstadoEquipo editEstadoEquipo(EstadoEquipo estadoEquipo);

    public void deleteEstadoEquipo(Long id);

    public EstadoEquipo getByNombreEstado(String nombre);
}
