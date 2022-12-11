package com.sangelp.dh.repository;

import com.sangelp.dh.domain.models.Odontologo;
import com.sangelp.dh.domain.models.Paciente;
import com.sangelp.dh.domain.models.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TurnosRepository extends JpaRepository<Turno,Long> {

    List<Turno> findByOdontologoOrPaciente(Odontologo odontologo, Paciente paciente);

}
