package com.devalb.wellbing.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devalb.wellbing.entities.Redes;
import com.devalb.wellbing.repository.RedesRepository;
import com.devalb.wellbing.service.RedesService;

@Service
public class RedesServiceImpl implements RedesService {

    @Autowired
    private RedesRepository redesRepository;

    @Override
    public List<Redes> getRedes() {
        return redesRepository.findAll();
    }

    @Override
    public Redes getRedesById(Long id) {
        return redesRepository.findById(id).orElse(null);
    }

    @Override
    public Redes addRedes(Redes redes) {
        return redesRepository.save(redes);
    }

    @Override
    public Redes editRedes(Redes redes) {
        return redesRepository.save(redes);
    }

    @Override
    public void deleteRedes(Long id) {
        redesRepository.deleteById(id);
    }

}
