package org.seguritech.cp.service.impl;

import org.seguritech.cp.service.ConsultaPlacaService;
import org.seguritech.cp.domain.ConsultaPlaca;
import org.seguritech.cp.repository.ConsultaPlacaRepository;
import org.seguritech.cp.service.dto.ConsultaPlacaDTO;
import org.seguritech.cp.service.mapper.ConsultaPlacaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Service Implementation for managing ConsultaPlaca.
 */
@Service
@Transactional
public class ConsultaPlacaServiceImpl implements ConsultaPlacaService{

    private final Logger log = LoggerFactory.getLogger(ConsultaPlacaServiceImpl.class);

    private final ConsultaPlacaRepository consultaPlacaRepository;

    private final ConsultaPlacaMapper consultaPlacaMapper;

    public ConsultaPlacaServiceImpl(ConsultaPlacaRepository consultaPlacaRepository, ConsultaPlacaMapper consultaPlacaMapper) {
        this.consultaPlacaRepository = consultaPlacaRepository;
        this.consultaPlacaMapper = consultaPlacaMapper;
    }

    /**
     * Save a consultaPlaca.
     *
     * @param consultaPlacaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ConsultaPlacaDTO save(ConsultaPlacaDTO consultaPlacaDTO) {
        log.debug("Request to save ConsultaPlaca : {}", consultaPlacaDTO);
        ConsultaPlaca consultaPlaca = consultaPlacaMapper.toEntity(consultaPlacaDTO);
        consultaPlaca = consultaPlacaRepository.save(consultaPlaca);
        return consultaPlacaMapper.toDto(consultaPlaca);
    }

    /**
     * Get all the consultaPlacas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ConsultaPlacaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ConsultaPlacas");
        return consultaPlacaRepository.findAll(pageable)
            .map(consultaPlacaMapper::toDto);
    }

    @Override
    public List<ConsultaPlacaDTO> findAll() {
        return consultaPlacaMapper.toDto(consultaPlacaRepository.findAll());
    }

    /**
     * Get one consultaPlaca by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ConsultaPlacaDTO findOne(Long id) {
        log.debug("Request to get ConsultaPlaca : {}", id);
        ConsultaPlaca consultaPlaca = consultaPlacaRepository.findOne(id);
        return consultaPlacaMapper.toDto(consultaPlaca);
    }

    /**
     * Delete the consultaPlaca by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ConsultaPlaca : {}", id);
        consultaPlacaRepository.delete(id);
    }
}
