package org.seguritech.cp.service.impl;

import org.seguritech.cp.service.MunicipioService;
import org.seguritech.cp.domain.Municipio;
import org.seguritech.cp.repository.MunicipioRepository;
import org.seguritech.cp.service.dto.MunicipioDTO;
import org.seguritech.cp.service.mapper.MunicipioMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Municipio.
 */
@Service
@Transactional
public class MunicipioServiceImpl implements MunicipioService{

    private final Logger log = LoggerFactory.getLogger(MunicipioServiceImpl.class);

    private final MunicipioRepository municipioRepository;

    private final MunicipioMapper municipioMapper;

    public MunicipioServiceImpl(MunicipioRepository municipioRepository, MunicipioMapper municipioMapper) {
        this.municipioRepository = municipioRepository;
        this.municipioMapper = municipioMapper;
    }

    /**
     * Save a municipio.
     *
     * @param municipioDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MunicipioDTO save(MunicipioDTO municipioDTO) {
        log.debug("Request to save Municipio : {}", municipioDTO);
        Municipio municipio = municipioMapper.toEntity(municipioDTO);
        municipio = municipioRepository.save(municipio);
        return municipioMapper.toDto(municipio);
    }

    /**
     * Get all the municipios.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MunicipioDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Municipios");
        return municipioRepository.findAll(pageable)
            .map(municipioMapper::toDto);
    }

    /**
     * Get one municipio by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MunicipioDTO findOne(Long id) {
        log.debug("Request to get Municipio : {}", id);
        Municipio municipio = municipioRepository.findOne(id);
        return municipioMapper.toDto(municipio);
    }

    /**
     * Delete the municipio by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Municipio : {}", id);
        municipioRepository.delete(id);
    }
}
