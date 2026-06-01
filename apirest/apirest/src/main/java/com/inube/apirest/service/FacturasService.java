package com.inube.apirest.service;

import com.inube.apirest.model.Facturas;
import com.inube.apirest.repository.FacturasRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FacturasService {
    private final FacturasRepository facturasRepository;

    public FacturasService(FacturasRepository facturasRepository) {
        this.facturasRepository = facturasRepository;
    }

    public List<Facturas> findAll() {
        return facturasRepository.findAll();
    }

    public List<Facturas> findActivos() {
        return facturasRepository.findByActivo(1);
    }

    public List<Facturas> findInactivos() {
        return facturasRepository.findByActivo(0);
    }

    public Optional<Facturas> findById(Long id) {
        return facturasRepository.findById(id);
    }

    public Facturas save(Facturas factura) {
        return facturasRepository.save(factura);
    }

    public void inactivar(Long id) {
        Facturas factura = facturasRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));

        factura.setActivo(0);

        facturasRepository.save(factura);
    }

    public void activar(Long id) {
        Facturas factura = facturasRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));

        factura.setActivo(1);
        facturasRepository.save(factura);
    }
}
