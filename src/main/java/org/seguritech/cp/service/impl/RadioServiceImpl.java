package org.seguritech.cp.service.impl;

import org.seguritech.cp.service.RadioService;
import org.seguritech.cp.domain.Radio;
import org.seguritech.cp.repository.RadioRepository;
import org.seguritech.cp.service.dto.RadioDTO;
import org.seguritech.cp.service.mapper.RadioMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Radio.
 */
@Service
@Transactional
public class RadioServiceImpl implements RadioService{

    private final Logger log = LoggerFactory.getLogger(RadioServiceImpl.class);

    private final RadioRepository radioRepository;

    private final RadioMapper radioMapper;

    public RadioServiceImpl(RadioRepository radioRepository, RadioMapper radioMapper) {
        this.radioRepository = radioRepository;
        this.radioMapper = radioMapper;
    }

    /**
     * Save a radio.
     *
     * @param radioDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RadioDTO save(RadioDTO radioDTO) {
        log.debug("Request to save Radio : {}", radioDTO);
        Radio radio = radioMapper.toEntity(radioDTO);
        radio = radioRepository.save(radio);
        return radioMapper.toDto(radio);
    }

    /**
     * Get all the radios.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RadioDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Radios");
        return radioRepository.findAll(pageable)
            .map(radioMapper::toDto);
    }

    /**
     * Get one radio by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public RadioDTO findOne(Long id) {
        log.debug("Request to get Radio : {}", id);
        Radio radio = radioRepository.findOne(id);
        return radioMapper.toDto(radio);
    }

    /**
     * Delete the radio by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Radio : {}", id);
        radioRepository.delete(id);
    }
}
