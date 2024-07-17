package com.devalb.wellbing.service;

import java.util.List;

import com.devalb.wellbing.entities.TipoPqrs;

public interface TipoPqrsService {

    public List<TipoPqrs> getTiposPqrs();

    public TipoPqrs getTipoPqrsById(Long id);

    public TipoPqrs addTipoPqrs(TipoPqrs tipoPqrs);

    public TipoPqrs editTipoPqrs(TipoPqrs tipoPqrs);

    public void deleteTipoPqrs(Long id);

}
