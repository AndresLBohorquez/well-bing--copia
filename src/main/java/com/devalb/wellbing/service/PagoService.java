package com.devalb.wellbing.service;

import java.util.List;

import com.devalb.wellbing.entities.Pago;

public interface PagoService {

    public List<Pago> getPagos();

    public Pago getPagoById(Long id);

    public Pago addPago(Pago pago);

    public Pago editPago(Pago pago);

    public void deletePago(Long id);
}
