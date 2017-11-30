package org.seguritech.cp.service;

import org.seguritech.cp.service.dto.TipoRadioDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing TipoRadio.
 */
public interface TipoRadioService {

    /**
     * Save a tipoRadio.
     *
     * @param tipoRadioDTO the entity to save
     * @return the persisted entity
     */
    TipoRadioDTO save(TipoRadioDTO tipoRadioDTO);

    /**
     * Get all the tipoRadios.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TipoRadioDTO> findAll(Pageable pageable);

    /**
     * Get the "id" tipoRadio.
     *
     * @param id the id of the entity
     * @return the entity
     */
    TipoRadioDTO findOne(Long id);

    /**
     * Delete the "id" tipoRadio.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
