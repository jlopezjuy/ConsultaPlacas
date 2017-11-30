package org.seguritech.cp.service.impl;

import org.seguritech.cp.service.TipoRadioService;
import org.seguritech.cp.domain.TipoRadio;
import org.seguritech.cp.repository.TipoRadioRepository;
import org.seguritech.cp.service.dto.TipoRadioDTO;
import org.seguritech.cp.service.mapper.TipoRadioMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing TipoRadio.
 */
@Service
@Transactional
public class TipoRadioServiceImpl implements TipoRadioService{

    private final Logger log = LoggerFactory.getLogger(TipoRadioServiceImpl.class);

    private final TipoRadioRepository tipoRadioRepository;

    private final TipoRadioMapper tipoRadioMapper;

    public TipoRadioServiceImpl(TipoRadioRepository tipoRadioRepository, TipoRadioMapper tipoRadioMapper) {
        this.tipoRadioRepository = tipoRadioRepository;
        this.tipoRadioMapper = tipoRadioMapper;
    }

    /**
     * Save a tipoRadio.
     *
     * @param tipoRadioDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TipoRadioDTO save(TipoRadioDTO tipoRadioDTO) {
        log.debug("Request to save TipoRadio : {}", tipoRadioDTO);
        TipoRadio tipoRadio = tipoRadioMapper.toEntity(tipoRadioDTO);
        tipoRadio = tipoRadioRepository.save(tipoRadio);
        return tipoRadioMapper.toDto(tipoRadio);
    }

    /**
     * Get all the tipoRadios.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TipoRadioDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TipoRadios");
        return tipoRadioRepository.findAll(pageable)
            .map(tipoRadioMapper::toDto);
    }

    /**
     * Get one tipoRadio by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TipoRadioDTO findOne(Long id) {
        log.debug("Request to get TipoRadio : {}", id);
        TipoRadio tipoRadio = tipoRadioRepository.findOne(id);
        return tipoRadioMapper.toDto(tipoRadio);
    }

    /**
     * Delete the tipoRadio by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TipoRadio : {}", id);
        tipoRadioRepository.delete(id);
    }
}
