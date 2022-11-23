package com.sangelp.dh.domain.services;

import com.sangelp.dh.domain.models.Odontologo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OdonotoloRepository extends JpaRepository<Odontologo,Long> {
}
