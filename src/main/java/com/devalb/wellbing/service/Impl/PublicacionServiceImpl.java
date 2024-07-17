package com.devalb.wellbing.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devalb.wellbing.entities.Publicacion;
import com.devalb.wellbing.repository.PublicacionRepository;
import com.devalb.wellbing.service.PublicacionService;

@Service
public class PublicacionServiceImpl implements PublicacionService {

    @Autowired
    private PublicacionRepository publicacionRepository;

    @Override
    public List<Publicacion> getPublicaciones() {
        return publicacionRepository.findAll();
    }

    @Override
    public Publicacion getPublicacionById(Long id) {
        return publicacionRepository.findById(id).orElse(null);
    }

    @Override
    public Publicacion addPublicacion(Publicacion publicacion) {
        return publicacionRepository.save(publicacion);
    }

    @Override
    public Publicacion editPublicacion(Publicacion publicacion) {
        return publicacionRepository.save(publicacion);
    }

    @Override
    public void deletePublicacion(Long id) {
        Publicacion publicacion = publicacionRepository.findById(id).orElse(null);
        if (publicacion != null) {
            publicacion.setVisible(false);
            publicacionRepository.save(publicacion);
        }
    }

    @Override
    public List<Publicacion> getAllByIdUsuario(Long id) {
        return publicacionRepository.findAllByIdUsuario(id);
    }

    @Override
    public List<Publicacion> getAllVisible() {
        return publicacionRepository.findAllVisible();
    }

}
