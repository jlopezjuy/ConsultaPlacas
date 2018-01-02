package org.seguritech.cp.service;

import org.seguritech.cp.service.dto.MarcaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Marca.
 */
public interface MarcaService {

    /**
     * Save a marca.
     *
     * @param marcaDTO the entity to save
     * @return the persisted entity
     */
    MarcaDTO save(MarcaDTO marcaDTO);

    /**
     * Get all the marcas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MarcaDTO> findAll(Pageable pageable);

    /**
     * Get the "id" marca.
     *
     * @param id the id of the entity
     * @return the entity
     */
    MarcaDTO findOne(Long id);

    /**
     * Delete the "id" marca.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
