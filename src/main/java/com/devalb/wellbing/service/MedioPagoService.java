package com.devalb.wellbing.service;

import java.util.List;

import com.devalb.wellbing.entities.MedioPago;

public interface MedioPagoService {

    public List<MedioPago> getMedioPagos();

    public MedioPago getMedioPagoById(Long id);

    public MedioPago addMedioPago(MedioPago medioPago);

    public MedioPago editMedioPago(MedioPago medioPago);

    public void deleteMedioPago(Long id);

    public List<MedioPago> getMedioPagoByIdUsuario(Long id);
}
