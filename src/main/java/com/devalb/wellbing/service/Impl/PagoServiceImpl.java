package com.devalb.wellbing.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devalb.wellbing.entities.Pago;
import com.devalb.wellbing.repository.PagoRepository;
import com.devalb.wellbing.service.PagoService;

@Service
public class PagoServiceImpl implements PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    @Override
    public List<Pago> getPagos() {
        return pagoRepository.findAll();
    }

    @Override
    public Pago getPagoById(Long id) {
        return pagoRepository.findById(id).orElse(null);
    }

    @Override
    public Pago addPago(Pago pago) {
        return pagoRepository.save(pago);
    }

    @Override
    public Pago editPago(Pago pago) {
        return pagoRepository.save(pago);
    }

    @Override
    public void deletePago(Long id) {
        pagoRepository.deleteById(id);
    }

}
