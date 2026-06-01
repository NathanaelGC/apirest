package com.inube.apirest.repository;

import com.inube.apirest.model.Facturas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FacturasRepository extends JpaRepository<Facturas, Long>{
    Optional<Facturas> findById(Long id);
    List<Facturas> findByActivo(Integer activo);
}