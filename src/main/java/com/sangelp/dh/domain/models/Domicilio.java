package com.sangelp.dh.domain.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "domicilios")
public class Domicilio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "domicilio_id")
    private Long id;

    @NotNull(message = "La calle no debe estar vacío.")
    private String calle;

    @NotNull(message = "El número no debe estar vacío.")
    private String numero;

    @NotNull(message = "La localidad no debe estar vacío.")
    private String localidad;

    @NotNull(message = "La provincia no debe estar vacío.")
    private String provincia;

    private String direccionCompleta;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "domicilio")
    private Set<Paciente> pacientes;

    public void direccionCompleta(){
        this.direccionCompleta = this.calle + " " + this.numero + " - " + this.localidad + ", " + this.provincia;
    }
}
