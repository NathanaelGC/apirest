package com.inube.apirest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

@Table(name = "FACTURAS")
public class Facturas {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "factura_seq")
    @SequenceGenerator(name = "factura_seq", sequenceName = "SEC_ID_FACTURA", allocationSize = 1)
    @Column(name = "ID_FACTURA", nullable = false)
    private Long idFactura;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "ID_CLIENTE", nullable = false,
            referencedColumnName = "ID_CLIENTE",
            foreignKey = @jakarta.persistence.ForeignKey(name = "FK_ID_CLIENTE_FACTURAS"))
    private Cliente cliente;

    @Column(name = "MONTO_TOTAL", nullable = false, precision = 8, scale = 2)
    private BigDecimal montoTotal;

    @Column(name = "FECHA_FACTURA")
    private LocalDate fechaFactura = LocalDate.now();

    @Column(name = "ACTIVO", nullable = false)
    private  Integer activo = 1;

    @Override
    public String toString() {
        return "MontoTotal{" +
                "id=" + idFactura +
                ", montoTotal='" + montoTotal + '\'' +
                ", clienteId=" + (cliente != null ? cliente.getIdCliente() : "null") +
                '}';
    }
}
