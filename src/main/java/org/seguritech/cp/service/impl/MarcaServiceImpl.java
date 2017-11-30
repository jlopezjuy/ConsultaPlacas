package org.seguritech.cp.service.impl;

import org.seguritech.cp.service.MarcaService;
import org.seguritech.cp.domain.Marca;
import org.seguritech.cp.repository.MarcaRepository;
import org.seguritech.cp.service.dto.MarcaDTO;
import org.seguritech.cp.service.mapper.MarcaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Marca.
 */
@Service
@Transactional
public class MarcaServiceImpl implements MarcaService{

    private final Logger log = LoggerFactory.getLogger(MarcaServiceImpl.class);

    private final MarcaRepository marcaRepository;

    private final MarcaMapper marcaMapper;

    public MarcaServiceImpl(MarcaRepository marcaRepository, MarcaMapper marcaMapper) {
        this.marcaRepository = marcaRepository;
        this.marcaMapper = marcaMapper;
    }

    /**
     * Save a marca.
     *
     * @param marcaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MarcaDTO save(MarcaDTO marcaDTO) {
        log.debug("Request to save Marca : {}", marcaDTO);
        Marca marca = marcaMapper.toEntity(marcaDTO);
        marca = marcaRepository.save(marca);
        return marcaMapper.toDto(marca);
    }

    /**
     * Get all the marcas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MarcaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Marcas");
        return marcaRepository.findAll(pageable)
            .map(marcaMapper::toDto);
    }

    /**
     * Get one marca by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MarcaDTO findOne(Long id) {
        log.debug("Request to get Marca : {}", id);
        Marca marca = marcaRepository.findOne(id);
        return marcaMapper.toDto(marca);
    }

    /**
     * Delete the marca by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Marca : {}", id);
        marcaRepository.delete(id);
    }
}
