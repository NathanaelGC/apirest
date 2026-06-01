package com.inube.apirest.service;

import com.inube.apirest.model.Cliente;
import com.inube.apirest.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// @Service indica que esta clase pertenece a la capa de servicio.
// Aqui se implementa la lógica del negocio (reglas, validaciones, flujos).
@Service
public class ClienteService {

    // Dependencia del repositorio que maneja la BD.
    private final ClienteRepository clienteRepository;

    // Inyección de dependencias por constructor (buena práctica).
    // Spring inyecta automáticamente un ClienteRepository cuando crea el servicio.
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    // Obtiene todos los Clientes de la base de datos.
    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    // Regresa únicamente clientes activos (ACTIVO = 1)
    public List<Cliente> findActivos() {
        return clienteRepository.findByActivo(1);
    }

    // Regresa únicamente clientes inactivos (ACTIVO = 0)
    public List<Cliente> findInactivos() {
        return clienteRepository.findByActivo(0);
    }

    // Busca un cliente por ID usando el método básico de JpaRepository.
    public Optional<Cliente> findById(Long id) {
        return clienteRepository.findById(id);
    }

    // Igual que findById, pero cargando también la lista de teléfonos.
    public Optional<Cliente> findByIdWithPhones(Long id) {
        return clienteRepository.findByIdWithPhones(id);
    }

    // Guarda o actualiza un cliente.
    // Si el ID es nulo -> crea uno nuevo.
    // Si el ID existe -> actualiza el registro.
    public Cliente save(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    // ❌ En lugar de borrar registros físicamente, se "desactiva" el cliente.
    // Esto es una buena práctica llamada "borrado lógico".
    public void inactivar(Long id) {
        // Busca el cliente; si no lo encuentra, lanza excepción.
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));

        // Cambia el estado a inactivo.
        cliente.setActivo(0);

        // Guarda el cambio en la BD.
        clienteRepository.save(cliente);
    }

    // Activa un cliente previamente desactivado.
    public void activar(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));

        cliente.setActivo(1);
        clienteRepository.save(cliente);
    }

}
