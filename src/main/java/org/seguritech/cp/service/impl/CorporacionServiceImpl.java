package org.seguritech.cp.service.impl;

import org.seguritech.cp.service.CorporacionService;
import org.seguritech.cp.domain.Corporacion;
import org.seguritech.cp.repository.CorporacionRepository;
import org.seguritech.cp.service.dto.CorporacionDTO;
import org.seguritech.cp.service.mapper.CorporacionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Corporacion.
 */
@Service
@Transactional
public class CorporacionServiceImpl implements CorporacionService{

    private final Logger log = LoggerFactory.getLogger(CorporacionServiceImpl.class);

    private final CorporacionRepository corporacionRepository;

    private final CorporacionMapper corporacionMapper;

    public CorporacionServiceImpl(CorporacionRepository corporacionRepository, CorporacionMapper corporacionMapper) {
        this.corporacionRepository = corporacionRepository;
        this.corporacionMapper = corporacionMapper;
    }

    /**
     * Save a corporacion.
     *
     * @param corporacionDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CorporacionDTO save(CorporacionDTO corporacionDTO) {
        log.debug("Request to save Corporacion : {}", corporacionDTO);
        Corporacion corporacion = corporacionMapper.toEntity(corporacionDTO);
        corporacion = corporacionRepository.save(corporacion);
        return corporacionMapper.toDto(corporacion);
    }

    /**
     * Get all the corporacions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CorporacionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Corporacions");
        return corporacionRepository.findAll(pageable)
            .map(corporacionMapper::toDto);
    }

    /**
     * Get one corporacion by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CorporacionDTO findOne(Long id) {
        log.debug("Request to get Corporacion : {}", id);
        Corporacion corporacion = corporacionRepository.findOne(id);
        return corporacionMapper.toDto(corporacion);
    }

    /**
     * Delete the corporacion by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Corporacion : {}", id);
        corporacionRepository.delete(id);
    }
}
