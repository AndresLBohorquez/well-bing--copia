package com.devalb.wellbing.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devalb.wellbing.entities.EstadoPqrs;
import com.devalb.wellbing.repository.EstadoPqrsRepository;
import com.devalb.wellbing.service.EstadoPqrsService;

@Service
public class EstadoPqrsServiceImpl implements EstadoPqrsService {

    @Autowired
    private EstadoPqrsRepository estadoPqrsRepository;

    @Override
    public List<EstadoPqrs> getEstadosPqrs() {
        return estadoPqrsRepository.findAll();
    }

    @Override
    public EstadoPqrs getEstadoPqrsById(Long id) {
        return estadoPqrsRepository.findById(id).orElse(null);
    }

    @Override
    public EstadoPqrs addEstadoPqrs(EstadoPqrs estadoPqrs) {
        return estadoPqrsRepository.save(estadoPqrs);
    }

    @Override
    public EstadoPqrs editEstadoPqrs(EstadoPqrs estadoPqrs) {
        return estadoPqrsRepository.save(estadoPqrs);
    }

    @Override
    public void deleteEstadoPqrs(Long id) {
        estadoPqrsRepository.deleteById(id);
    }

}
