package com.inube.apirest.repository;

import com.inube.apirest.model.Telefono;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TelefonoRepository extends JpaRepository<Telefono, Long>{
    Optional<Telefono> findById(Long id);
    List<Telefono> findByActivo(Integer activo);
}
