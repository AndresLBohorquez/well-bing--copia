package com.devalb.wellbing.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devalb.wellbing.entities.Pqrs;
import com.devalb.wellbing.repository.PqrsRepository;
import com.devalb.wellbing.service.PqrsService;

@Service
public class PqrsServiceImpl implements PqrsService {

    @Autowired
    private PqrsRepository pqrsRepository;

    @Override
    public List<Pqrs> getPqrs() {
        return pqrsRepository.findAll();
    }

    @Override
    public Pqrs getPqrsById(Long id) {
        return pqrsRepository.findById(id).orElse(null);
    }

    @Override
    public Pqrs addPqrs(Pqrs pqrs) {
        return pqrsRepository.save(pqrs);
    }

    @Override
    public Pqrs editPqrs(Pqrs pqrs) {
        return pqrsRepository.save(pqrs);
    }

    @Override
    public void deletePqrs(Long id) {
        pqrsRepository.deleteById(id);
    }

    @Override
    public List<Pqrs> getPqrsByIdUsuario(Long id) {
        return pqrsRepository.findByIdUsuario(id);
    }

}
