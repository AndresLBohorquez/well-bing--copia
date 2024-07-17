package com.devalb.wellbing.service;

import java.util.List;

import com.devalb.wellbing.entities.Mensaje;

public interface MensajeService {

    public List<Mensaje> getMensajes();

    public Mensaje getMensajeById(Long id);

    public Mensaje addMensaje(Mensaje mensaje);

    public Mensaje editMensaje(Mensaje mensaje);

    public void deleteMensaje(Long id);

    public List<Mensaje> getByIdPqrs(Long id);
}
