package com.devalb.wellbing.service;

import java.util.List;

import com.devalb.wellbing.entities.Rol;

public interface RolService {

    public List<Rol> getRoles();

    public Rol getRolById(Long id);

    public Rol addRol(Rol rol);

    public Rol editRol(Rol rol);

    public void deleteRol(Long id);
}
