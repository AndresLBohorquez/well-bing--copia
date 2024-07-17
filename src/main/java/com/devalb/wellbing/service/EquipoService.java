package com.devalb.wellbing.service;

import java.util.List;

import com.devalb.wellbing.entities.Equipo;

public interface EquipoService {

    public List<Equipo> getEquipos();

    public Equipo getEquipoById(Long id);

    public Equipo addEquipo(Equipo equipo);

    public Equipo editEquipo(Equipo equipo);

    public void deleteEquipo(Long id);

    public List<Equipo> getEquipoByIdUsuario(Long id);

    public List<Equipo> getEquiposVisibles();

}