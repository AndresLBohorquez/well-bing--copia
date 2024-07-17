package com.devalb.wellbing.service;

import java.util.List;

import com.devalb.wellbing.entities.Publicacion;

public interface PublicacionService {

    public List<Publicacion> getPublicaciones();

    public Publicacion getPublicacionById(Long id);

    public Publicacion addPublicacion(Publicacion publicacion);

    public Publicacion editPublicacion(Publicacion publicacion);

    public void deletePublicacion(Long id);

    public List<Publicacion> getAllByIdUsuario(Long id);

    public List<Publicacion> getAllVisible();

}
