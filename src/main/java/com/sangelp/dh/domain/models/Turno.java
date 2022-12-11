package com.sangelp.dh.domain.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "turnos")
@SQLDelete(sql = "UPDATE turnos SET deleted = true WHERE turno_id = ?")
@Where(clause = "deleted = false")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
public class Turno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "turno_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "odontologo_id")
    private Odontologo odontologo;

    //@NotNull(message = "La fecha no puede estar vacía.")
    private LocalDateTime date;

    private LocalDateTime created = LocalDateTime.now(ZoneId.of("America/Bogota"));
    private boolean deleted = Boolean.FALSE;
}
