package com.inube.apirest.service;

import com.inube.apirest.model.Facturas;
import com.inube.apirest.model.Pago;
import com.inube.apirest.repository.PagoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class PagoService {
    private final PagoRepository pagoRepository;
    private final FacturasService facturasService;

    public PagoService(PagoRepository pagoRepository, FacturasService facturasService) {
        this.pagoRepository = pagoRepository;
        this.facturasService = facturasService;
    }

    public List<Pago> findAll() {
        return pagoRepository.findAll();
    }

    public List<Pago> findActivos() {
        return pagoRepository.findByActivo(1);
    }

    public List<Pago> findInactivos() {
        return pagoRepository.findByActivo(0);
    }

    public Optional<Pago> findById(Long id) {
        return pagoRepository.findById(id);
    }

    public Pago save(Pago pago) {
        Facturas factura = facturasService.findById(pago.getFactura().getIdFactura())
                .orElseThrow(() -> new IllegalArgumentException("Factura no encontrada"));
        BigDecimal sumaPagos = pagoRepository
                .findByFacturaIdFacturaAndActivo(factura.getIdFactura(), 1)
                .stream()
                .filter(p -> !p.getIdPago().equals(pago.getIdPago()))
                .map(Pago::getMonto)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalProyectado = sumaPagos.add(pago.getMonto());
        if (totalProyectado.compareTo(factura.getMontoTotal()) > 0) {
            throw new RuntimeException("El pago excede el saldo pendiente. Máximo permitido: "
                    + factura.getMontoTotal().subtract(sumaPagos));
        }

        return pagoRepository.save(pago);
    }

    public void inactivar(Long id) {
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));

        pago.setActivo(0);

        pagoRepository.save(pago);
    }

    public void activar(Long id) {
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));

        pago.setActivo(1);
        pagoRepository.save(pago);
    }
}
