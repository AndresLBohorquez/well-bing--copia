package com.devalb.wellbing.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devalb.wellbing.entities.MedioPago;
import com.devalb.wellbing.repository.MedioPagoRepository;
import com.devalb.wellbing.service.MedioPagoService;

@Service
public class MedioPagoServiceImpl implements MedioPagoService {

    @Autowired
    private MedioPagoRepository mpr;

    @Override
    public List<MedioPago> getMedioPagos() {
        return mpr.findAll();
    }

    @Override
    public MedioPago getMedioPagoById(Long id) {
        return mpr.findById(id).orElse(null);
    }

    @Override
    public MedioPago addMedioPago(MedioPago medioPago) {
        return mpr.save(medioPago);
    }

    @Override
    public MedioPago editMedioPago(MedioPago medioPago) {
        return mpr.save(medioPago);
    }

    @Override
    public void deleteMedioPago(Long id) {
        mpr.deleteById(id);
    }

    @Override
    public List<MedioPago> getMedioPagoByIdUsuario(Long id) {
        return mpr.findAllByIdUsuario(id);
    }

}
