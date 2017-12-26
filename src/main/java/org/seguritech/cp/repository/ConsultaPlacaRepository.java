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

    @Query(value = "SELECT cp " +
        "FROM ConsultaPlaca cp " +
        "INNER JOIN Radio ON " +
        " cp.radio_issi = Radio.issi " +
        "INNER JOIN Municipio ON " +
        " Radio.municipio_id = Municipio.id " +
        "INNER JOIN Corporacion ON " +
        " Radio.corporacion_id = Corporacion.id " +
        "where cp.radio.issi = :issi " +
        "and cp.estado = :estado " +
        "and cp.radio.municipio.descripcion = :municipio " +
        "and cp.radio.corporacion.descripcion =:corporacion " +
        "and cp.fecha between :desde and :hasta")
    List<ConsultaPlaca> findAllByRadio(@Param("issi") Long issi,
                                       @Param("municipio") String municipio,
                                       @Param("corporacion") String corporacion,
                                       @Param("estado") Boolean estado,
                                       @Param("desde") LocalDate desde,
                                       @Param("hasta")LocalDate hasta);
}
