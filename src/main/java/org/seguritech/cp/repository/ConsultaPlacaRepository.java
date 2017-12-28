package org.seguritech.cp.repository;

import org.seguritech.cp.domain.ConsultaPlaca;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;
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
        "WHERE (:issi is null OR cp.radio.issi = :issi) " +
        "AND (:estado is null OR cp.estado = :estado) " +
        "AND (:municipio is null OR cp.radio.municipio.descripcion = :municipio )" +
        "AND (:corporacion is null OR cp.radio.corporacion.descripcion =:corporacion )" +
        "AND cp.fecha between :desde and :hasta")
    List<ConsultaPlaca> findAllByRadio(@Param("issi") Long issi,
                                       @Param("municipio") String municipio,
                                       @Param("corporacion") String corporacion,
                                       @Param("estado") Boolean estado,
                                       @Param("desde") LocalDate desde,
                                       @Param("hasta") LocalDate hasta);

    @Query("SELECT cp " +
        "FROM ConsultaPlaca cp " +
        "INNER JOIN Radio radio ON cp.radio.issi = radio.issi " +
        "INNER JOIN Municipio mun ON radio.municipio.id = mun.id " +
        "INNER JOIN Corporacion corp ON radio.corporacion.id = corp.id " +
        "WHERE (:issi is null OR cp.radio.issi = :issi) " +
        "AND (:estado is null OR cp.estado = :estado) " +
        "AND (:municipio is null OR cp.radio.municipio.descripcion = :municipio )" +
        "AND (:corporacion is null OR cp.radio.corporacion.descripcion =:corporacion )" +
        "AND cp.fecha between :desde and :desde")
    List<ConsultaPlaca> findAllByRadioDesde(@Param("issi") Long issi,
                                       @Param("municipio") String municipio,
                                       @Param("corporacion") String corporacion,
                                       @Param("estado") Boolean estado,
                                       @Param("desde") LocalDate desde);

    @Query("SELECT cp " +
        "FROM ConsultaPlaca cp " +
        "INNER JOIN Radio radio ON cp.radio.issi = radio.issi " +
        "INNER JOIN Municipio mun ON radio.municipio.id = mun.id " +
        "INNER JOIN Corporacion corp ON radio.corporacion.id = corp.id " +
        "WHERE (:issi is null OR cp.radio.issi = :issi) " +
        "AND (:estado is null OR cp.estado = :estado) " +
        "AND (:municipio is null OR cp.radio.municipio.descripcion = :municipio )" +
        "AND (:corporacion is null OR cp.radio.corporacion.descripcion =:corporacion )" +
        "AND cp.fecha between :hasta and :hasta")
    List<ConsultaPlaca> findAllByRadioHasta(@Param("issi") Long issi,
                                       @Param("municipio") String municipio,
                                       @Param("corporacion") String corporacion,
                                       @Param("estado") Boolean estado,
                                       @Param("hasta")LocalDate hasta);

    @Query("SELECT cp " +
        "FROM ConsultaPlaca cp " +
        "INNER JOIN Radio radio ON cp.radio.issi = radio.issi " +
        "INNER JOIN Municipio mun ON radio.municipio.id = mun.id " +
        "INNER JOIN Corporacion corp ON radio.corporacion.id = corp.id " +
        "WHERE (:issi is null OR cp.radio.issi = :issi) " +
        "AND (:estado is null OR cp.estado = :estado) " +
        "AND (:municipio is null OR cp.radio.municipio.descripcion = :municipio )" +
        "AND (:corporacion is null OR cp.radio.corporacion.descripcion =:corporacion )")
    List<ConsultaPlaca> findAllByRadioSinFecha(@Param("issi") Long issi,
                                       @Param("municipio") String municipio,
                                       @Param("corporacion") String corporacion,
                                       @Param("estado") Boolean estado);

    /****/

    @Query("SELECT cp " +
        "FROM ConsultaPlaca cp " +
        "INNER JOIN Radio radio ON cp.radio.issi = radio.issi " +
        "INNER JOIN Municipio mun ON radio.municipio.id = mun.id " +
        "INNER JOIN Corporacion corp ON radio.corporacion.id = corp.id " +
        "WHERE (:issi is null OR cp.radio.issi = :issi) " +
        "AND (:estado is null OR cp.estado = :estado) " +
        "AND (:municipio is null OR cp.radio.municipio.descripcion = :municipio )" +
        "AND (:corporacion is null OR cp.radio.corporacion.descripcion =:corporacion )" +
        "AND cp.fecha between :desde and :hasta")
    Page<ConsultaPlaca> findAllByRadio(@Param("issi") Long issi,
                                       @Param("municipio") String municipio,
                                       @Param("corporacion") String corporacion,
                                       @Param("estado") Boolean estado,
                                       @Param("desde") LocalDate desde,
                                       @Param("hasta") LocalDate hasta,
                                       Pageable var1);

    @Query("SELECT cp " +
        "FROM ConsultaPlaca cp " +
        "INNER JOIN Radio radio ON cp.radio.issi = radio.issi " +
        "INNER JOIN Municipio mun ON radio.municipio.id = mun.id " +
        "INNER JOIN Corporacion corp ON radio.corporacion.id = corp.id " +
        "WHERE (:issi is null OR cp.radio.issi = :issi) " +
        "AND (:estado is null OR cp.estado = :estado) " +
        "AND (:municipio is null OR cp.radio.municipio.descripcion = :municipio )" +
        "AND (:corporacion is null OR cp.radio.corporacion.descripcion =:corporacion )" +
        "AND cp.fecha between :desde and :desde")
    Page<ConsultaPlaca> findAllByRadioDesde(@Param("issi") Long issi,
                                            @Param("municipio") String municipio,
                                            @Param("corporacion") String corporacion,
                                            @Param("estado") Boolean estado,
                                            @Param("desde") LocalDate desde,
                                            Pageable var1);

    @Query("SELECT cp " +
        "FROM ConsultaPlaca cp " +
        "INNER JOIN Radio radio ON cp.radio.issi = radio.issi " +
        "INNER JOIN Municipio mun ON radio.municipio.id = mun.id " +
        "INNER JOIN Corporacion corp ON radio.corporacion.id = corp.id " +
        "WHERE (:issi is null OR cp.radio.issi = :issi) " +
        "AND (:estado is null OR cp.estado = :estado) " +
        "AND (:municipio is null OR cp.radio.municipio.descripcion = :municipio )" +
        "AND (:corporacion is null OR cp.radio.corporacion.descripcion =:corporacion )" +
        "AND cp.fecha between :hasta and :hasta")
    Page<ConsultaPlaca> findAllByRadioHasta(@Param("issi") Long issi,
                                            @Param("municipio") String municipio,
                                            @Param("corporacion") String corporacion,
                                            @Param("estado") Boolean estado,
                                            @Param("hasta")LocalDate hasta,
                                            Pageable var1);

    @Query("SELECT cp " +
        "FROM ConsultaPlaca cp " +
        "INNER JOIN Radio radio ON cp.radio.issi = radio.issi " +
        "INNER JOIN Municipio mun ON radio.municipio.id = mun.id " +
        "INNER JOIN Corporacion corp ON radio.corporacion.id = corp.id " +
        "WHERE (:issi is null OR cp.radio.issi = :issi) " +
        "AND (:estado is null OR cp.estado = :estado) " +
        "AND (:municipio is null OR cp.radio.municipio.descripcion = :municipio )" +
        "AND (:corporacion is null OR cp.radio.corporacion.descripcion =:corporacion )")
    Page<ConsultaPlaca> findAllByRadioSinFecha(@Param("issi") Long issi,
                                               @Param("municipio") String municipio,
                                               @Param("corporacion") String corporacion,
                                               @Param("estado") Boolean estado,
                                               Pageable var1);
}
