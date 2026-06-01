package com.inube.apirest.repository;

import com.inube.apirest.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long>{
    List<Pago> findByFacturaIdFacturaAndActivo(Long idFactura, Integer activo);
    Optional<Pago> findById(Long id);
    List<Pago> findByActivo(Integer activo);
}
