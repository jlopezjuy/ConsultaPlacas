package org.seguritech.cp.service;

import org.seguritech.cp.service.dto.ConsultaPlacaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.List;

/**
 * Service Interface for managing ConsultaPlaca.
 */
public interface ConsultaPlacaService {

    /**
     * Save a consultaPlaca.
     *
     * @param consultaPlacaDTO the entity to save
     * @return the persisted entity
     */
    ConsultaPlacaDTO save(ConsultaPlacaDTO consultaPlacaDTO);

    /**
     * Get all the consultaPlacas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ConsultaPlacaDTO> findAll(Pageable pageable);

    /**
     * Get all the consultaPlacas.
     *
     * @return the list of entities
     */
    List<ConsultaPlacaDTO> findAll();

    /**
     * Get the "id" consultaPlaca.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ConsultaPlacaDTO findOne(Long id);

    /**
     * Delete the "id" consultaPlaca.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    ModelAndView getReportByType(String type,
                                 String issi,
                                 String municipio,
                                 String corporacion,
                                 String estado,
                                 LocalDate desde,
                                 LocalDate hasta);
}
