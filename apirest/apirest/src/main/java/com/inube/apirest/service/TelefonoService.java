package com.inube.apirest.service;

import com.inube.apirest.model.Telefono;
import com.inube.apirest.repository.TelefonoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TelefonoService {
    private final TelefonoRepository telefonoRepository;

    public TelefonoService(TelefonoRepository telefonoRepository) {
        this.telefonoRepository = telefonoRepository;
    }

    public List<Telefono> findAll() {
        return telefonoRepository.findAll();
    }

    public List<Telefono> findActivos() {
        return telefonoRepository.findByActivo(1);
    }

    public List<Telefono> findInactivos() {
        return telefonoRepository.findByActivo(0);
    }

    public Optional<Telefono> findById(Long id) {
        return telefonoRepository.findById(id);
    }

    public Telefono save(Telefono telefono) {
        return telefonoRepository.save(telefono);
    }

    public void inactivar(Long id) {
        Telefono telefono = telefonoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));

        telefono.setActivo(0);

        telefonoRepository.save(telefono);
    }

    public void activar(Long id) {
        Telefono telefono = telefonoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));

        telefono.setActivo(1);
        telefonoRepository.save(telefono);
    }
}
