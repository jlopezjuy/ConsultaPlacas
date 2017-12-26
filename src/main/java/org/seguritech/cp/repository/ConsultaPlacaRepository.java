package org.seguritech.cp.repository;

import org.seguritech.cp.domain.ConsultaPlaca;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.time.LocalDate;
import java.util.List;


/**
 * Spring Data JPA repository for the ConsultaPlaca entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConsultaPlacaRepository extends JpaRepository<ConsultaPlaca, Long> {

    @Query(value = "select * from ConsultaPlaca cp inner join cp.radio radio inner join radio.municipio mun inner join radio.corporacion corp where radio.issi = :issi" +
        "and mun.descripcion = :municipio and corp.descripcion =:corporacion")
    List<ConsultaPlaca> findAllByRadio(@Param("issi") Long issi,
                                       @Param("municipio") String municipio,
                                       @Param("corporacion") String corporacion,
                                       @Param("estado") Boolean estado,
                                       @Param("desde") LocalDate desde,
                                       @Param("hasta")LocalDate hasta);
}
