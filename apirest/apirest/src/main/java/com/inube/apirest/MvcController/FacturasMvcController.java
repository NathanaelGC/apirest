package com.inube.apirest.MvcController;

import com.inube.apirest.model.Cliente;
import com.inube.apirest.model.Facturas;
import com.inube.apirest.service.ClienteService;
import com.inube.apirest.service.FacturasService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller

@RequestMapping("/web/facturas")
public class FacturasMvcController {

    private final FacturasService facturasService;
    private final ClienteService clienteService;

    public FacturasMvcController(FacturasService facturasService, ClienteService clienteService) {
        this.facturasService = facturasService;
        this.clienteService = clienteService;
    }

    @GetMapping
    public String listarFacturas(Model model) {
        model.addAttribute("facturas", facturasService.findAll());
        model.addAttribute("filtro", "todos");
        return "facturas/list";
    }

    @GetMapping("/activos")
    public String listarActivos(Model model) {
        model.addAttribute("facturas", facturasService.findActivos());
        model.addAttribute("filtro", "activos");
        return "facturas/list";
    }

    @GetMapping("/inactivos")
    public String listarInactivos(Model model) {
        model.addAttribute("facturas", facturasService.findInactivos());
        model.addAttribute("filtro", "inactivos");
        return "facturas/list";
    }

    @GetMapping({"/new", "/edit/{id}"})
    public String showFormularioFacturas(@PathVariable(required = false) Long id,
                                         @RequestParam(required = false) Long idCliente, Model model) {

        Facturas factura;
        if (id != null) {

            factura = facturasService.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
            model.addAttribute("action", "edit");
        } else {
            factura = new Facturas();
            factura.setActivo(1);

            Cliente cliente = clienteService.findById(idCliente)
                    .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado: " + idCliente));
            factura.setCliente(cliente);

            model.addAttribute("action", "new");
        }

        model.addAttribute("factura", factura);
        return "facturas/form";
    }

    @PostMapping
    public String saveFactura(@ModelAttribute Facturas factura) {
        facturasService.save(factura);
        return "redirect:/web/facturas";
    }

    @GetMapping("/{id}")
    public String showDetallesFactura(@PathVariable Long id, Model model) {
        Facturas factura = facturasService.findById(id).orElseThrow(
                () -> new IllegalArgumentException("ID de la factura inválido:" + id)
        );
        model.addAttribute("factura", factura);

        return "facturas/show";
    }

    @GetMapping("/inactivar/{id}")
    public String inactivarFactura(@PathVariable Long id) {
        facturasService.inactivar(id);
        return "redirect:/web/facturas";
    }

    @GetMapping("/activar/{id}")
    public String activarFactura(@PathVariable Long id) {
        facturasService.activar(id);
        return "redirect:/web/facturas";
    }
}
