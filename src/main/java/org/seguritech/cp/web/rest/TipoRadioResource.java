package org.seguritech.cp.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.seguritech.cp.service.TipoRadioService;
import org.seguritech.cp.web.rest.errors.BadRequestAlertException;
import org.seguritech.cp.web.rest.util.HeaderUtil;
import org.seguritech.cp.web.rest.util.PaginationUtil;
import org.seguritech.cp.service.dto.TipoRadioDTO;
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
 * REST controller for managing TipoRadio.
 */
@RestController
@RequestMapping("/api")
public class TipoRadioResource {

    private final Logger log = LoggerFactory.getLogger(TipoRadioResource.class);

    private static final String ENTITY_NAME = "tipoRadio";

    private final TipoRadioService tipoRadioService;

    public TipoRadioResource(TipoRadioService tipoRadioService) {
        this.tipoRadioService = tipoRadioService;
    }

    /**
     * POST  /tipo-radios : Create a new tipoRadio.
     *
     * @param tipoRadioDTO the tipoRadioDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tipoRadioDTO, or with status 400 (Bad Request) if the tipoRadio has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tipo-radios")
    @Timed
    public ResponseEntity<TipoRadioDTO> createTipoRadio(@Valid @RequestBody TipoRadioDTO tipoRadioDTO) throws URISyntaxException {
        log.debug("REST request to save TipoRadio : {}", tipoRadioDTO);
        if (tipoRadioDTO.getId() != null) {
            throw new BadRequestAlertException("A new tipoRadio cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoRadioDTO result = tipoRadioService.save(tipoRadioDTO);
        return ResponseEntity.created(new URI("/api/tipo-radios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tipo-radios : Updates an existing tipoRadio.
     *
     * @param tipoRadioDTO the tipoRadioDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tipoRadioDTO,
     * or with status 400 (Bad Request) if the tipoRadioDTO is not valid,
     * or with status 500 (Internal Server Error) if the tipoRadioDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tipo-radios")
    @Timed
    public ResponseEntity<TipoRadioDTO> updateTipoRadio(@Valid @RequestBody TipoRadioDTO tipoRadioDTO) throws URISyntaxException {
        log.debug("REST request to update TipoRadio : {}", tipoRadioDTO);
        if (tipoRadioDTO.getId() == null) {
            return createTipoRadio(tipoRadioDTO);
        }
        TipoRadioDTO result = tipoRadioService.save(tipoRadioDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tipoRadioDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tipo-radios : get all the tipoRadios.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tipoRadios in body
     */
    @GetMapping("/tipo-radios")
    @Timed
    public ResponseEntity<List<TipoRadioDTO>> getAllTipoRadios(Pageable pageable) {
        log.debug("REST request to get a page of TipoRadios");
        Page<TipoRadioDTO> page = tipoRadioService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tipo-radios");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tipo-radios/:id : get the "id" tipoRadio.
     *
     * @param id the id of the tipoRadioDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tipoRadioDTO, or with status 404 (Not Found)
     */
    @GetMapping("/tipo-radios/{id}")
    @Timed
    public ResponseEntity<TipoRadioDTO> getTipoRadio(@PathVariable Long id) {
        log.debug("REST request to get TipoRadio : {}", id);
        TipoRadioDTO tipoRadioDTO = tipoRadioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tipoRadioDTO));
    }

    /**
     * DELETE  /tipo-radios/:id : delete the "id" tipoRadio.
     *
     * @param id the id of the tipoRadioDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tipo-radios/{id}")
    @Timed
    public ResponseEntity<Void> deleteTipoRadio(@PathVariable Long id) {
        log.debug("REST request to delete TipoRadio : {}", id);
        tipoRadioService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
