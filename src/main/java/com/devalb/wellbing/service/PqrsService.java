package com.devalb.wellbing.service;

import java.util.List;

import com.devalb.wellbing.entities.Pqrs;

public interface PqrsService {

    public List<Pqrs> getPqrs();

    public Pqrs getPqrsById(Long id);

    public Pqrs addPqrs(Pqrs pqrs);

    public Pqrs editPqrs(Pqrs pqrs);

    public void deletePqrs(Long id);

    public List<Pqrs> getPqrsByIdUsuario(Long id);
}
