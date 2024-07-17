package com.devalb.wellbing.service;

import java.util.List;

import com.devalb.wellbing.entities.EstadoPqrs;

public interface EstadoPqrsService {

    public List<EstadoPqrs> getEstadosPqrs();

    public EstadoPqrs getEstadoPqrsById(Long id);

    public EstadoPqrs addEstadoPqrs(EstadoPqrs estadoPqrs);

    public EstadoPqrs editEstadoPqrs(EstadoPqrs estadoPqrs);

    public void deleteEstadoPqrs(Long id);
}
