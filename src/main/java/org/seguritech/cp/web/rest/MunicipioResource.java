package org.seguritech.cp.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.seguritech.cp.service.MunicipioService;
import org.seguritech.cp.web.rest.errors.BadRequestAlertException;
import org.seguritech.cp.web.rest.util.HeaderUtil;
import org.seguritech.cp.web.rest.util.PaginationUtil;
import org.seguritech.cp.service.dto.MunicipioDTO;
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
 * REST controller for managing Municipio.
 */
@RestController
@RequestMapping("/api")
public class MunicipioResource {

    private final Logger log = LoggerFactory.getLogger(MunicipioResource.class);

    private static final String ENTITY_NAME = "municipio";

    private final MunicipioService municipioService;

    public MunicipioResource(MunicipioService municipioService) {
        this.municipioService = municipioService;
    }

    /**
     * POST  /municipios : Create a new municipio.
     *
     * @param municipioDTO the municipioDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new municipioDTO, or with status 400 (Bad Request) if the municipio has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/municipios")
    @Timed
    public ResponseEntity<MunicipioDTO> createMunicipio(@Valid @RequestBody MunicipioDTO municipioDTO) throws URISyntaxException {
        log.debug("REST request to save Municipio : {}", municipioDTO);
        if (municipioDTO.getId() != null) {
            throw new BadRequestAlertException("A new municipio cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MunicipioDTO result = municipioService.save(municipioDTO);
        return ResponseEntity.created(new URI("/api/municipios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /municipios : Updates an existing municipio.
     *
     * @param municipioDTO the municipioDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated municipioDTO,
     * or with status 400 (Bad Request) if the municipioDTO is not valid,
     * or with status 500 (Internal Server Error) if the municipioDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/municipios")
    @Timed
    public ResponseEntity<MunicipioDTO> updateMunicipio(@Valid @RequestBody MunicipioDTO municipioDTO) throws URISyntaxException {
        log.debug("REST request to update Municipio : {}", municipioDTO);
        if (municipioDTO.getId() == null) {
            return createMunicipio(municipioDTO);
        }
        MunicipioDTO result = municipioService.save(municipioDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, municipioDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /municipios : get all the municipios.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of municipios in body
     */
    @GetMapping("/municipios")
    @Timed
    public ResponseEntity<List<MunicipioDTO>> getAllMunicipios(Pageable pageable) {
        log.debug("REST request to get a page of Municipios");
        Page<MunicipioDTO> page = municipioService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/municipios");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /municipios/:id : get the "id" municipio.
     *
     * @param id the id of the municipioDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the municipioDTO, or with status 404 (Not Found)
     */
    @GetMapping("/municipios/{id}")
    @Timed
    public ResponseEntity<MunicipioDTO> getMunicipio(@PathVariable Long id) {
        log.debug("REST request to get Municipio : {}", id);
        MunicipioDTO municipioDTO = municipioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(municipioDTO));
    }

    /**
     * DELETE  /municipios/:id : delete the "id" municipio.
     *
     * @param id the id of the municipioDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/municipios/{id}")
    @Timed
    public ResponseEntity<Void> deleteMunicipio(@PathVariable Long id) {
        log.debug("REST request to delete Municipio : {}", id);
        municipioService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
