package org.seguritech.cp.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.seguritech.cp.service.CorporacionService;
import org.seguritech.cp.web.rest.errors.BadRequestAlertException;
import org.seguritech.cp.web.rest.util.HeaderUtil;
import org.seguritech.cp.web.rest.util.PaginationUtil;
import org.seguritech.cp.service.dto.CorporacionDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Corporacion.
 */
@RestController
@RequestMapping("/api")
public class CorporacionResource {

    private final Logger log = LoggerFactory.getLogger(CorporacionResource.class);

    private static final String ENTITY_NAME = "corporacion";

    private final CorporacionService corporacionService;

    public CorporacionResource(CorporacionService corporacionService) {
        this.corporacionService = corporacionService;
    }

    /**
     * POST  /corporacions : Create a new corporacion.
     *
     * @param corporacionDTO the corporacionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new corporacionDTO, or with status 400 (Bad Request) if the corporacion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/corporacions")
    @Timed
    public ResponseEntity<CorporacionDTO> createCorporacion(@Valid @RequestBody CorporacionDTO corporacionDTO) throws URISyntaxException {
        log.debug("REST request to save Corporacion : {}", corporacionDTO);
        if (corporacionDTO.getId() != null) {
            throw new BadRequestAlertException("A new corporacion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CorporacionDTO result = corporacionService.save(corporacionDTO);
        return ResponseEntity.created(new URI("/api/corporacions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /corporacions : Updates an existing corporacion.
     *
     * @param corporacionDTO the corporacionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated corporacionDTO,
     * or with status 400 (Bad Request) if the corporacionDTO is not valid,
     * or with status 500 (Internal Server Error) if the corporacionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/corporacions")
    @Timed
    public ResponseEntity<CorporacionDTO> updateCorporacion(@Valid @RequestBody CorporacionDTO corporacionDTO) throws URISyntaxException {
        log.debug("REST request to update Corporacion : {}", corporacionDTO);
        if (corporacionDTO.getId() == null) {
            return createCorporacion(corporacionDTO);
        }
        CorporacionDTO result = corporacionService.save(corporacionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, corporacionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /corporacions : get all the corporacions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of corporacions in body
     */
    @GetMapping("/corporacions")
    @Timed
    public ResponseEntity<List<CorporacionDTO>> getAllCorporacions(Pageable pageable) {
        log.debug("REST request to get a page of Corporacions");
        Page<CorporacionDTO> page = corporacionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/corporacions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /corporacions/:id : get the "id" corporacion.
     *
     * @param id the id of the corporacionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the corporacionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/corporacions/{id}")
    @Timed
    public ResponseEntity<CorporacionDTO> getCorporacion(@PathVariable Long id) {
        log.debug("REST request to get Corporacion : {}", id);
        CorporacionDTO corporacionDTO = corporacionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(corporacionDTO));
    }

    /**
     * DELETE  /corporacions/:id : delete the "id" corporacion.
     *
     * @param id the id of the corporacionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/corporacions/{id}")
    @Timed
    public ResponseEntity<Void> deleteCorporacion(@PathVariable Long id) {
        log.debug("REST request to delete Corporacion : {}", id);
        corporacionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
