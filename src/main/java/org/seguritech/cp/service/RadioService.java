package org.seguritech.cp.service;

import org.seguritech.cp.service.dto.RadioDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Radio.
 */
public interface RadioService {

    /**
     * Save a radio.
     *
     * @param radioDTO the entity to save
     * @return the persisted entity
     */
    RadioDTO save(RadioDTO radioDTO);

    /**
     * Get all the radios.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<RadioDTO> findAll(Pageable pageable);

    /**
     * Get the "id" radio.
     *
     * @param id the id of the entity
     * @return the entity
     */
    RadioDTO findOne(Long issi);

    /**
     * Delete the "id" radio.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
