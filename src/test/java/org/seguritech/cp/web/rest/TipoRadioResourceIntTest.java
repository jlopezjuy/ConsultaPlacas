package org.seguritech.cp.web.rest;

import org.seguritech.cp.ConsultaPlacasApp;

import org.seguritech.cp.domain.TipoRadio;
import org.seguritech.cp.repository.TipoRadioRepository;
import org.seguritech.cp.service.TipoRadioService;
import org.seguritech.cp.service.dto.TipoRadioDTO;
import org.seguritech.cp.service.mapper.TipoRadioMapper;
import org.seguritech.cp.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.seguritech.cp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TipoRadioResource REST controller.
 *
 * @see TipoRadioResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConsultaPlacasApp.class)
public class TipoRadioResourceIntTest {

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    @Autowired
    private TipoRadioRepository tipoRadioRepository;

    @Autowired
    private TipoRadioMapper tipoRadioMapper;

    @Autowired
    private TipoRadioService tipoRadioService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTipoRadioMockMvc;

    private TipoRadio tipoRadio;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TipoRadioResource tipoRadioResource = new TipoRadioResource(tipoRadioService);
        this.restTipoRadioMockMvc = MockMvcBuilders.standaloneSetup(tipoRadioResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoRadio createEntity(EntityManager em) {
        TipoRadio tipoRadio = new TipoRadio()
            .descripcion(DEFAULT_DESCRIPCION);
        return tipoRadio;
    }

    @Before
    public void initTest() {
        tipoRadio = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoRadio() throws Exception {
        int databaseSizeBeforeCreate = tipoRadioRepository.findAll().size();

        // Create the TipoRadio
        TipoRadioDTO tipoRadioDTO = tipoRadioMapper.toDto(tipoRadio);
        restTipoRadioMockMvc.perform(post("/api/tipo-radios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoRadioDTO)))
            .andExpect(status().isCreated());

        // Validate the TipoRadio in the database
        List<TipoRadio> tipoRadioList = tipoRadioRepository.findAll();
        assertThat(tipoRadioList).hasSize(databaseSizeBeforeCreate + 1);
        TipoRadio testTipoRadio = tipoRadioList.get(tipoRadioList.size() - 1);
        assertThat(testTipoRadio.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    public void createTipoRadioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoRadioRepository.findAll().size();

        // Create the TipoRadio with an existing ID
        tipoRadio.setId(1L);
        TipoRadioDTO tipoRadioDTO = tipoRadioMapper.toDto(tipoRadio);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoRadioMockMvc.perform(post("/api/tipo-radios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoRadioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoRadio in the database
        List<TipoRadio> tipoRadioList = tipoRadioRepository.findAll();
        assertThat(tipoRadioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDescripcionIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoRadioRepository.findAll().size();
        // set the field null
        tipoRadio.setDescripcion(null);

        // Create the TipoRadio, which fails.
        TipoRadioDTO tipoRadioDTO = tipoRadioMapper.toDto(tipoRadio);

        restTipoRadioMockMvc.perform(post("/api/tipo-radios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoRadioDTO)))
            .andExpect(status().isBadRequest());

        List<TipoRadio> tipoRadioList = tipoRadioRepository.findAll();
        assertThat(tipoRadioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTipoRadios() throws Exception {
        // Initialize the database
        tipoRadioRepository.saveAndFlush(tipoRadio);

        // Get all the tipoRadioList
        restTipoRadioMockMvc.perform(get("/api/tipo-radios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoRadio.getId().intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())));
    }

    @Test
    @Transactional
    public void getTipoRadio() throws Exception {
        // Initialize the database
        tipoRadioRepository.saveAndFlush(tipoRadio);

        // Get the tipoRadio
        restTipoRadioMockMvc.perform(get("/api/tipo-radios/{id}", tipoRadio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tipoRadio.getId().intValue()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTipoRadio() throws Exception {
        // Get the tipoRadio
        restTipoRadioMockMvc.perform(get("/api/tipo-radios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoRadio() throws Exception {
        // Initialize the database
        tipoRadioRepository.saveAndFlush(tipoRadio);
        int databaseSizeBeforeUpdate = tipoRadioRepository.findAll().size();

        // Update the tipoRadio
        TipoRadio updatedTipoRadio = tipoRadioRepository.findOne(tipoRadio.getId());
        // Disconnect from session so that the updates on updatedTipoRadio are not directly saved in db
        em.detach(updatedTipoRadio);
        updatedTipoRadio
            .descripcion(UPDATED_DESCRIPCION);
        TipoRadioDTO tipoRadioDTO = tipoRadioMapper.toDto(updatedTipoRadio);

        restTipoRadioMockMvc.perform(put("/api/tipo-radios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoRadioDTO)))
            .andExpect(status().isOk());

        // Validate the TipoRadio in the database
        List<TipoRadio> tipoRadioList = tipoRadioRepository.findAll();
        assertThat(tipoRadioList).hasSize(databaseSizeBeforeUpdate);
        TipoRadio testTipoRadio = tipoRadioList.get(tipoRadioList.size() - 1);
        assertThat(testTipoRadio.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoRadio() throws Exception {
        int databaseSizeBeforeUpdate = tipoRadioRepository.findAll().size();

        // Create the TipoRadio
        TipoRadioDTO tipoRadioDTO = tipoRadioMapper.toDto(tipoRadio);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTipoRadioMockMvc.perform(put("/api/tipo-radios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoRadioDTO)))
            .andExpect(status().isCreated());

        // Validate the TipoRadio in the database
        List<TipoRadio> tipoRadioList = tipoRadioRepository.findAll();
        assertThat(tipoRadioList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTipoRadio() throws Exception {
        // Initialize the database
        tipoRadioRepository.saveAndFlush(tipoRadio);
        int databaseSizeBeforeDelete = tipoRadioRepository.findAll().size();

        // Get the tipoRadio
        restTipoRadioMockMvc.perform(delete("/api/tipo-radios/{id}", tipoRadio.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TipoRadio> tipoRadioList = tipoRadioRepository.findAll();
        assertThat(tipoRadioList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoRadio.class);
        TipoRadio tipoRadio1 = new TipoRadio();
        tipoRadio1.setId(1L);
        TipoRadio tipoRadio2 = new TipoRadio();
        tipoRadio2.setId(tipoRadio1.getId());
        assertThat(tipoRadio1).isEqualTo(tipoRadio2);
        tipoRadio2.setId(2L);
        assertThat(tipoRadio1).isNotEqualTo(tipoRadio2);
        tipoRadio1.setId(null);
        assertThat(tipoRadio1).isNotEqualTo(tipoRadio2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoRadioDTO.class);
        TipoRadioDTO tipoRadioDTO1 = new TipoRadioDTO();
        tipoRadioDTO1.setId(1L);
        TipoRadioDTO tipoRadioDTO2 = new TipoRadioDTO();
        assertThat(tipoRadioDTO1).isNotEqualTo(tipoRadioDTO2);
        tipoRadioDTO2.setId(tipoRadioDTO1.getId());
        assertThat(tipoRadioDTO1).isEqualTo(tipoRadioDTO2);
        tipoRadioDTO2.setId(2L);
        assertThat(tipoRadioDTO1).isNotEqualTo(tipoRadioDTO2);
        tipoRadioDTO1.setId(null);
        assertThat(tipoRadioDTO1).isNotEqualTo(tipoRadioDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(tipoRadioMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(tipoRadioMapper.fromId(null)).isNull();
    }
}
