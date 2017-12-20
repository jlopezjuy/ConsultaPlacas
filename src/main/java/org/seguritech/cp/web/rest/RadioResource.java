package org.seguritech.cp.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.seguritech.cp.service.RadioService;
import org.seguritech.cp.web.rest.errors.BadRequestAlertException;
import org.seguritech.cp.web.rest.util.HeaderUtil;
import org.seguritech.cp.web.rest.util.PaginationUtil;
import org.seguritech.cp.service.dto.RadioDTO;
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
 * REST controller for managing Radio.
 */
@RestController
@RequestMapping("/api")
public class RadioResource {

    private final Logger log = LoggerFactory.getLogger(RadioResource.class);

    private static final String ENTITY_NAME = "radio";

    private final RadioService radioService;

    public RadioResource(RadioService radioService) {
        this.radioService = radioService;
    }

    /**
     * POST  /radios : Create a new radio.
     *
     * @param radioDTO the radioDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new radioDTO, or with status 400 (Bad Request) if the radio has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/radios")
    @Timed
    public ResponseEntity<RadioDTO> createRadio(@Valid @RequestBody RadioDTO radioDTO) throws URISyntaxException {
        log.debug("REST request to save Radio : {}", radioDTO);
        if (radioDTO.getIssi() != null) {
            throw new BadRequestAlertException("A new radio cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RadioDTO result = radioService.save(radioDTO);
        return ResponseEntity.created(new URI("/api/radios/" + result.getIssi()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getIssi().toString()))
            .body(result);
    }

    /**
     * PUT  /radios : Updates an existing radio.
     *
     * @param radioDTO the radioDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated radioDTO,
     * or with status 400 (Bad Request) if the radioDTO is not valid,
     * or with status 500 (Internal Server Error) if the radioDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/radios")
    @Timed
    public ResponseEntity<RadioDTO> updateRadio(@Valid @RequestBody RadioDTO radioDTO) throws URISyntaxException {
        log.debug("REST request to update Radio : {}", radioDTO);
        if (radioDTO.getIssi() == null) {
            return createRadio(radioDTO);
        }
        RadioDTO result = radioService.save(radioDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, radioDTO.getIssi().toString()))
            .body(result);
    }

    /**
     * GET  /radios : get all the radios.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of radios in body
     */
    @GetMapping("/radios")
    @Timed
    public ResponseEntity<List<RadioDTO>> getAllRadios(Pageable pageable) {
        log.debug("REST request to get a page of Radios");
        Page<RadioDTO> page = radioService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/radios");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /radios/:id : get the "id" radio.
     *
     * @param issi the id of the radioDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the radioDTO, or with status 404 (Not Found)
     */
    @GetMapping("/radios/{issi}")
    @Timed
    public ResponseEntity<RadioDTO> getRadio(@PathVariable Long issi) {
        log.debug("REST request to get Radio : {}", issi);
        RadioDTO radioDTO = radioService.findOne(issi);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(radioDTO));
    }

    /**
     * DELETE  /radios/:issi : delete the "id" radio.
     *
     * @param issi the id of the radioDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/radios/{issi}")
    @Timed
    public ResponseEntity<Void> deleteRadio(@PathVariable Long issi) {
        log.debug("REST request to delete Radio : {}", issi);
        radioService.delete(issi);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, issi.toString())).build();
    }
}
