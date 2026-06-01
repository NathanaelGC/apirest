package com.inube.apirest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

@Table(name = "PAGOS")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pago_seq")
    @SequenceGenerator(name = "pago_seq", sequenceName = "SEC_ID_PAGO", allocationSize = 1)
    @Column(name = "ID_PAGO", nullable = false)
    private Long idPago;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "ID_FACTURA", nullable = false,
            referencedColumnName = "ID_FACTURA",
            foreignKey = @jakarta.persistence.ForeignKey(name = "FK_ID_FACTURA_PAGOS"))
    private Facturas factura;

    @Column(name = "MONTO", nullable = false, precision = 8, scale = 2)
    private BigDecimal monto;

    @Column(name = "ACTIVO", nullable = false)
    private  Integer activo = 1;

    @Override
    public String toString() {
        return "Monto{" +
                "id=" + idPago +
                ", monto='" + monto + '\'' +
                ", facturaId=" + (factura != null ? factura.getIdFactura() : "null") +
                '}';
    }
}
