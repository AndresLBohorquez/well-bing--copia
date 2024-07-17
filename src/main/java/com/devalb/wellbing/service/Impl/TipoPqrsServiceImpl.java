package com.devalb.wellbing.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devalb.wellbing.entities.TipoPqrs;
import com.devalb.wellbing.repository.TipoPqrsRepository;
import com.devalb.wellbing.service.TipoPqrsService;

@Service
public class TipoPqrsServiceImpl implements TipoPqrsService {

    @Autowired
    private TipoPqrsRepository tipoPqrsRepository;

    @Override
    public List<TipoPqrs> getTiposPqrs() {
        return tipoPqrsRepository.findAll();
    }

    @Override
    public TipoPqrs getTipoPqrsById(Long id) {
        return tipoPqrsRepository.findById(id).orElse(null);
    }

    @Override
    public TipoPqrs addTipoPqrs(TipoPqrs tipoPqrs) {
        return tipoPqrsRepository.save(tipoPqrs);
    }

    @Override
    public TipoPqrs editTipoPqrs(TipoPqrs tipoPqrs) {
        return tipoPqrsRepository.save(tipoPqrs);
    }

    @Override
    public void deleteTipoPqrs(Long id) {
        tipoPqrsRepository.deleteById(id);
    }

}
