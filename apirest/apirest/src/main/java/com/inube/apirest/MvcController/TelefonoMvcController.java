package com.inube.apirest.MvcController;

import com.inube.apirest.model.Cliente;
import com.inube.apirest.model.Telefono;
import com.inube.apirest.service.ClienteService;
import com.inube.apirest.service.TelefonoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller

@RequestMapping("/web/telefonos")
public class TelefonoMvcController {

    private final TelefonoService telefonoService;
    private final ClienteService clienteService;

    public TelefonoMvcController(TelefonoService telefonoService, ClienteService clienteService) {
        this.telefonoService = telefonoService;
        this.clienteService = clienteService;
    }

    @GetMapping
    public String listarTelefonos(Model model) {
        model.addAttribute("telefonos", telefonoService.findAll());
        model.addAttribute("filtro", "todos");
        return "telefonos/list";
    }

    @GetMapping("/activos")
    public String listarActivos(Model model) {
        model.addAttribute("telefonos", telefonoService.findActivos());
        model.addAttribute("filtro", "activos");
        return "telefonos/list";
    }

    @GetMapping("/inactivos")
    public String listarInactivos(Model model) {
        model.addAttribute("telefonos", telefonoService.findInactivos());
        model.addAttribute("filtro", "inactivos");
        return "telefonos/list";
    }

    @GetMapping({"/new", "/edit/{id}"})
    public String showFormularioTelefono(@PathVariable(required = false) Long id,
                                         @RequestParam(required = false) Long idCliente, Model model) {

        Telefono telefono;
        if (id != null) {

            telefono = telefonoService.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
            model.addAttribute("action", "edit");
        } else {
            telefono = new Telefono();
            telefono.setActivo(1);

            Cliente cliente = clienteService.findById(idCliente)
                    .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado: " + idCliente));
                telefono.setCliente(cliente);

            model.addAttribute("action", "new");
        }

        model.addAttribute("telefono", telefono);
        return "telefonos/form";
    }

    @PostMapping
    public String saveTelefono(@ModelAttribute Telefono telefono) {
        telefonoService.save(telefono);
        return "redirect:/web/telefonos";
    }

    @GetMapping("/{id}")
    public String showDetallesTelefono(@PathVariable Long id, Model model) {
        Telefono telefono = telefonoService.findById(id).orElseThrow(
                () -> new IllegalArgumentException("ID de teléfono inválido:" + id)
        );
        model.addAttribute("telefono", telefono);

        return "telefonos/show";
    }

    @GetMapping("/inactivar/{id}")
    public String inactivarTelefono(@PathVariable Long id) {
        telefonoService.inactivar(id);
        return "redirect:/web/telefonos";
    }

    @GetMapping("/activar/{id}")
    public String activarTelefono(@PathVariable Long id) {
        telefonoService.activar(id);
        return "redirect:/web/telefonos";
    }
}
