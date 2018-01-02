package org.seguritech.cp.service;

import org.seguritech.cp.service.dto.MunicipioDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Municipio.
 */
public interface MunicipioService {

    /**
     * Save a municipio.
     *
     * @param municipioDTO the entity to save
     * @return the persisted entity
     */
    MunicipioDTO save(MunicipioDTO municipioDTO);

    /**
     * Get all the municipios.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MunicipioDTO> findAll(Pageable pageable);

    /**
     * Get the "id" municipio.
     *
     * @param id the id of the entity
     * @return the entity
     */
    MunicipioDTO findOne(Long id);

    /**
     * Delete the "id" municipio.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
