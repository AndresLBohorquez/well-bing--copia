package com.devalb.wellbing.service;

import java.util.List;

import com.devalb.wellbing.entities.Redes;

public interface RedesService {

    public List<Redes> getRedes();

    public Redes getRedesById(Long id);

    public Redes addRedes(Redes redes);

    public Redes editRedes(Redes redes);

    public void deleteRedes(Long id);
}
