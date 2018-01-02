package org.seguritech.cp.service;

import org.seguritech.cp.service.dto.CorporacionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Corporacion.
 */
public interface CorporacionService {

    /**
     * Save a corporacion.
     *
     * @param corporacionDTO the entity to save
     * @return the persisted entity
     */
    CorporacionDTO save(CorporacionDTO corporacionDTO);

    /**
     * Get all the corporacions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CorporacionDTO> findAll(Pageable pageable);

    /**
     * Get the "id" corporacion.
     *
     * @param id the id of the entity
     * @return the entity
     */
    CorporacionDTO findOne(Long id);

    /**
     * Delete the "id" corporacion.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
