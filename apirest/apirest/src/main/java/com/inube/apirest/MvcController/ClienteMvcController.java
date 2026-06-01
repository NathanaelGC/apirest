package com.inube.apirest.MvcController;

import com.inube.apirest.model.Cliente;
import com.inube.apirest.service.ClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// @Controller indica que esta clase pertenece a la capa web/MVC.
// Se usa para devolver vistas (HTML) con ModelAndView o Model.
@Controller

// Prefijo comun para todas las rutas del controlador.
// Ejemplo: /web/clientes, /web/clientes/activos, etc
@RequestMapping("/web/clientes")
public class ClienteMvcController {

    // Inyección del servicio donde está la lógica de negocio.
    private final ClienteService clienteService;

    // Inyección por constructor (recomendada).
    public ClienteMvcController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    // ================================
    //  LISTADO DE CLIENTES
    // ================================

    // GET /web/clientes
    // Lista todos los clientes.
    @GetMapping
    public String listarClientes(Model model) {
        model.addAttribute("clientes", clienteService.findAll());
        model.addAttribute("filtro", "todos"); // ayuda a mostrar el estado del filtro en la vista
        return "clientes/list"; // retorna la vista list.html
    }

    // GET /web/clientes/activos
    @GetMapping("/activos")
    public String listarActivos(Model model) {
        model.addAttribute("clientes", clienteService.findActivos());
        model.addAttribute("filtro", "activos");
        return "clientes/list";
    }

    // GET /web/clientes/inactivos
    @GetMapping("/inactivos")
    public String listarInactivos(Model model) {
        model.addAttribute("clientes", clienteService.findInactivos());
        model.addAttribute("filtro", "inactivos");
        return "clientes/list";
    }

    // ================================
    //   FORMULARIO DE NUEVO / EDITAR
    // ================================

    // GET /web/clientes/new................ nuevo cliente
    // GET /web/clientes/edit/{id}.......... editar cliente existente
    @GetMapping({"/new", "/edit/{id}"})
    public String showFormularioCliente(@PathVariable(required = false) Long id, Model model) {

        Cliente cliente;

        if (id != null) {
            // Si viene ID → editar
            cliente = clienteService.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
            model.addAttribute("action", "edit");
        } else {
            // Si NO viene ID → crear nuevo
            cliente = new Cliente();
            cliente.setActivo(1);
            // por defecto activo
            model.addAttribute("action", "new");
        }

        model.addAttribute("cliente", cliente);
        return "clientes/form"; // muestra form.html
    }

    // ================================
    // GUARDAR CLIENTE (CREATE/UPDATE)
    // ================================

    // POST /web/clientes
    // Spring llena automáticamente el objeto Cliente con los valores del formulario.
    @PostMapping
    public String saveCliente(@ModelAttribute Cliente cliente) {
        clienteService.save(cliente);
        // Después de guardar redirige al listado principal.
        return "redirect:/web/clientes";
    }

    // ================================
    // DETALLES DE UN CLIENTE
    // ================================

    // GET /web/clientes/{id}
    @GetMapping("/{id}")
    public String showDetallesCliente(@PathVariable Long id, Model model) {

        // Usa el método que carga también los teléfonos (JOIN FETCH)
        Cliente cliente = clienteService.findByIdWithPhones(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado: " + id));

        model.addAttribute("cliente", cliente);
        return "clientes/show"; // muestra show.html
    }

    // ================================
    // ACTIVAR / DESACTIVAR CLIENTE
    // ================================

    // GET /web/clientes/inactivar/{id}
    @GetMapping("/inactivar/{id}")
    public String inactivarCliente(@PathVariable Long id) {
        clienteService.inactivar(id);
        return "redirect:/web/clientes";
    }

    // GET /web/clientes/activar/{id}
    @GetMapping("/activar/{id}")
    public String activarCliente(@PathVariable Long id) {
        clienteService.activar(id);
        return "redirect:/web/clientes";
    }
}
