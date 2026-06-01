package com.inube.apirest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data // Incluye @Getter, @Setter, @ToString, @EqualsAndHashCode, y @RequiredArgsConstructor
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
@Entity

@Table(name = "TELEFONOS")
public class Telefono {

    @Id
    // Configuración para bases de datos Oracle (secuencia)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "telefono_seq")
    @SequenceGenerator(name = "telefono_seq", sequenceName = "SEC_ID_TELEFONO", allocationSize = 1)
    @Column(name = "ID_TELEFONO", nullable = false)
    private Long idTelefono; // Usamos Long para el ID de tipo NUMBER

    // Relación con la tabla CLIENTES
    // Muchos teléfonos pueden pertenecer a un Cliente (ManyToOne)
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "ID_CLIENTE", nullable = false,
            referencedColumnName = "ID_CLIENTE",
            foreignKey = @jakarta.persistence.ForeignKey(name = "FK_ID_CLIENTE_TELEFONOS"))
    private Cliente cliente; // Asume que tienes una entidad Cliente

    @Column(name = "TELEFONO", nullable = false, length = 15)
    private String telefono;

    @Column(name = "ACTIVO", nullable = false)
    private  Integer activo = 1;

    @Override
    public String toString() {
        return "Telefono{" +
                "id=" + idTelefono +
                ", numero='" + telefono + '\'' +
                ", clienteId=" + (cliente != null ? cliente.getIdCliente() : "null") +
                '}';
    }
}