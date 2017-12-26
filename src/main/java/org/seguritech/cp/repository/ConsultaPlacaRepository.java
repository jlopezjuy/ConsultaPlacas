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

    @Query("SELECT cp " +
        "FROM ConsultaPlaca cp " +
        "INNER JOIN Radio radio ON cp.radio.issi = radio.issi " +
        "INNER JOIN Municipio mun ON radio.municipio.id = mun.id " +
        "INNER JOIN Corporacion corp ON radio.corporacion.id = corp.id " +
        "WHERE cp.radio.issi = :issi " +
        "AND cp.estado = :estado " +
        "AND cp.radio.municipio.descripcion = :municipio " +
        "AND cp.radio.corporacion.descripcion =:corporacion " +
        "AND cp.fecha between :desde and :hasta")
    List<ConsultaPlaca> findAllByRadio(@Param("issi") Long issi,
                                       @Param("municipio") String municipio,
                                       @Param("corporacion") String corporacion,
                                       @Param("estado") Boolean estado,
                                       @Param("desde") LocalDate desde,
                                       @Param("hasta")LocalDate hasta);
}
