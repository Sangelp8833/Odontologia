package com.sangelp.dh.repository;

import com.sangelp.dh.domain.models.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TurnosRepository extends JpaRepository<Turno,Long> {
}
