package com.sangelp.dh.repository;

import com.sangelp.dh.domain.models.Odontologo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OdontologoRepository extends JpaRepository<Odontologo,Long> {

        Odontologo findByMatricula(Long matricula);

}
