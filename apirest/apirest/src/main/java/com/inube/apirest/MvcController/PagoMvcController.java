package com.inube.apirest.MvcController;

import com.inube.apirest.model.Facturas;
import com.inube.apirest.model.Pago;
import com.inube.apirest.service.FacturasService;
import com.inube.apirest.service.PagoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller

@RequestMapping("/web/pagos")
public class PagoMvcController {
    private final PagoService pagoService;
    private final FacturasService facturasService;

    public PagoMvcController(PagoService pagoService, FacturasService facturasService) {
        this.pagoService = pagoService;
        this.facturasService = facturasService;
    }

    @GetMapping
    public String listarPagos(Model model) {
        model.addAttribute("pago", pagoService.findAll());
        model.addAttribute("filtro", "todos");
        return "pagos/list";
    }

    @GetMapping("/activos")
    public String listarActivos(Model model) {
        model.addAttribute("pago", pagoService.findActivos());
        model.addAttribute("filtro", "activos");
        return "pagos/list";
    }

    @GetMapping("/inactivos")
    public String listarInactivos(Model model) {
        model.addAttribute("pago", pagoService.findInactivos());
        model.addAttribute("filtro", "inactivos");
        return "pagos/list";
    }

    @GetMapping({"/new", "/edit/{id}"})
    public String showFormularioPago(@PathVariable(required = false) Long id,
                                         @RequestParam(required = false) Long idFactura, Model model) {

        Pago pago;
        if (id != null) {

            pago = pagoService.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
            model.addAttribute("action", "edit");
        } else {
            pago = new Pago();
            pago.setActivo(1);

            Facturas facturas = facturasService.findById(idFactura)
                    .orElseThrow(() -> new IllegalArgumentException("Factura no encontrado: " + idFactura));
            pago.setFactura(facturas);

            model.addAttribute("action", "new");
        }

        model.addAttribute("pago", pago);
        return "pagos/form";
    }

    @PostMapping
    public String savePago(@ModelAttribute Pago pago, Model model) {
        try {
            pagoService.save(pago);
            return "redirect:/web/pagos";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("pago", pago);
            model.addAttribute("action", pago.getIdPago() != null ? "edit" : "new");
            return "pagos/form";
        }
    }

    @GetMapping("/{id}")
    public String showDetallesPago(@PathVariable Long id, Model model) {
        Pago pago = pagoService.findById(id).orElseThrow(
                () -> new IllegalArgumentException("ID de pago inválido:" + id)
        );
        model.addAttribute("pago", pago);

        return "pagos/show";
    }

    @GetMapping("/inactivar/{id}")
    public String inactivarTelefono(@PathVariable Long id) {
        pagoService.inactivar(id);
        return "redirect:/web/pagos";
    }

    @GetMapping("/activar/{id}")
    public String activarTelefono(@PathVariable Long id) {
        pagoService.activar(id);
        return "redirect:/web/pagos";
    }
}
