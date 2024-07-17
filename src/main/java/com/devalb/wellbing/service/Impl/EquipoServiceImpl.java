package com.devalb.wellbing.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devalb.wellbing.entities.Equipo;
import com.devalb.wellbing.repository.EquipoRepository;
import com.devalb.wellbing.service.EquipoService;

@Service
public class EquipoServiceImpl implements EquipoService {

    @Autowired
    private EquipoRepository equipoRepository;

    @Override
    public List<Equipo> getEquipos() {
        return equipoRepository.findAll();
    }

    @Override
    public Equipo getEquipoById(Long id) {
        return equipoRepository.findById(id).orElse(null);
    }

    @Override
    public Equipo addEquipo(Equipo equipo) {
        return equipoRepository.save(equipo);
    }

    @Override
    public Equipo editEquipo(Equipo equipo) {
        return equipoRepository.save(equipo);
    }

    @Override
    public void deleteEquipo(Long id) {
        Equipo equipo = equipoRepository.findById(id).orElse(null);
        if (equipo != null) {
            equipo.setVisible(false);
            equipoRepository.save(equipo);
        }
    }

    @Override
    public List<Equipo> getEquipoByIdUsuario(Long id) {
        return equipoRepository.findAllByIdUsuario(id);
    }

    @Override
    public List<Equipo> getEquiposVisibles() {
        return equipoRepository.findAllByVisible();
    }

}
