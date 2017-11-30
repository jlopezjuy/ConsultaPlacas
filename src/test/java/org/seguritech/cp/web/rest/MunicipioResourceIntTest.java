package org.seguritech.cp.web.rest;

import org.seguritech.cp.ConsultaPlacasApp;

import org.seguritech.cp.domain.Municipio;
import org.seguritech.cp.repository.MunicipioRepository;
import org.seguritech.cp.service.MunicipioService;
import org.seguritech.cp.service.dto.MunicipioDTO;
import org.seguritech.cp.service.mapper.MunicipioMapper;
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
 * Test class for the MunicipioResource REST controller.
 *
 * @see MunicipioResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConsultaPlacasApp.class)
public class MunicipioResourceIntTest {

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    @Autowired
    private MunicipioRepository municipioRepository;

    @Autowired
    private MunicipioMapper municipioMapper;

    @Autowired
    private MunicipioService municipioService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMunicipioMockMvc;

    private Municipio municipio;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MunicipioResource municipioResource = new MunicipioResource(municipioService);
        this.restMunicipioMockMvc = MockMvcBuilders.standaloneSetup(municipioResource)
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
    public static Municipio createEntity(EntityManager em) {
        Municipio municipio = new Municipio()
            .descripcion(DEFAULT_DESCRIPCION);
        return municipio;
    }

    @Before
    public void initTest() {
        municipio = createEntity(em);
    }

    @Test
    @Transactional
    public void createMunicipio() throws Exception {
        int databaseSizeBeforeCreate = municipioRepository.findAll().size();

        // Create the Municipio
        MunicipioDTO municipioDTO = municipioMapper.toDto(municipio);
        restMunicipioMockMvc.perform(post("/api/municipios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(municipioDTO)))
            .andExpect(status().isCreated());

        // Validate the Municipio in the database
        List<Municipio> municipioList = municipioRepository.findAll();
        assertThat(municipioList).hasSize(databaseSizeBeforeCreate + 1);
        Municipio testMunicipio = municipioList.get(municipioList.size() - 1);
        assertThat(testMunicipio.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    public void createMunicipioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = municipioRepository.findAll().size();

        // Create the Municipio with an existing ID
        municipio.setId(1L);
        MunicipioDTO municipioDTO = municipioMapper.toDto(municipio);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMunicipioMockMvc.perform(post("/api/municipios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(municipioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Municipio in the database
        List<Municipio> municipioList = municipioRepository.findAll();
        assertThat(municipioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDescripcionIsRequired() throws Exception {
        int databaseSizeBeforeTest = municipioRepository.findAll().size();
        // set the field null
        municipio.setDescripcion(null);

        // Create the Municipio, which fails.
        MunicipioDTO municipioDTO = municipioMapper.toDto(municipio);

        restMunicipioMockMvc.perform(post("/api/municipios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(municipioDTO)))
            .andExpect(status().isBadRequest());

        List<Municipio> municipioList = municipioRepository.findAll();
        assertThat(municipioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMunicipios() throws Exception {
        // Initialize the database
        municipioRepository.saveAndFlush(municipio);

        // Get all the municipioList
        restMunicipioMockMvc.perform(get("/api/municipios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(municipio.getId().intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())));
    }

    @Test
    @Transactional
    public void getMunicipio() throws Exception {
        // Initialize the database
        municipioRepository.saveAndFlush(municipio);

        // Get the municipio
        restMunicipioMockMvc.perform(get("/api/municipios/{id}", municipio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(municipio.getId().intValue()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMunicipio() throws Exception {
        // Get the municipio
        restMunicipioMockMvc.perform(get("/api/municipios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMunicipio() throws Exception {
        // Initialize the database
        municipioRepository.saveAndFlush(municipio);
        int databaseSizeBeforeUpdate = municipioRepository.findAll().size();

        // Update the municipio
        Municipio updatedMunicipio = municipioRepository.findOne(municipio.getId());
        // Disconnect from session so that the updates on updatedMunicipio are not directly saved in db
        em.detach(updatedMunicipio);
        updatedMunicipio
            .descripcion(UPDATED_DESCRIPCION);
        MunicipioDTO municipioDTO = municipioMapper.toDto(updatedMunicipio);

        restMunicipioMockMvc.perform(put("/api/municipios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(municipioDTO)))
            .andExpect(status().isOk());

        // Validate the Municipio in the database
        List<Municipio> municipioList = municipioRepository.findAll();
        assertThat(municipioList).hasSize(databaseSizeBeforeUpdate);
        Municipio testMunicipio = municipioList.get(municipioList.size() - 1);
        assertThat(testMunicipio.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void updateNonExistingMunicipio() throws Exception {
        int databaseSizeBeforeUpdate = municipioRepository.findAll().size();

        // Create the Municipio
        MunicipioDTO municipioDTO = municipioMapper.toDto(municipio);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMunicipioMockMvc.perform(put("/api/municipios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(municipioDTO)))
            .andExpect(status().isCreated());

        // Validate the Municipio in the database
        List<Municipio> municipioList = municipioRepository.findAll();
        assertThat(municipioList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMunicipio() throws Exception {
        // Initialize the database
        municipioRepository.saveAndFlush(municipio);
        int databaseSizeBeforeDelete = municipioRepository.findAll().size();

        // Get the municipio
        restMunicipioMockMvc.perform(delete("/api/municipios/{id}", municipio.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Municipio> municipioList = municipioRepository.findAll();
        assertThat(municipioList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Municipio.class);
        Municipio municipio1 = new Municipio();
        municipio1.setId(1L);
        Municipio municipio2 = new Municipio();
        municipio2.setId(municipio1.getId());
        assertThat(municipio1).isEqualTo(municipio2);
        municipio2.setId(2L);
        assertThat(municipio1).isNotEqualTo(municipio2);
        municipio1.setId(null);
        assertThat(municipio1).isNotEqualTo(municipio2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MunicipioDTO.class);
        MunicipioDTO municipioDTO1 = new MunicipioDTO();
        municipioDTO1.setId(1L);
        MunicipioDTO municipioDTO2 = new MunicipioDTO();
        assertThat(municipioDTO1).isNotEqualTo(municipioDTO2);
        municipioDTO2.setId(municipioDTO1.getId());
        assertThat(municipioDTO1).isEqualTo(municipioDTO2);
        municipioDTO2.setId(2L);
        assertThat(municipioDTO1).isNotEqualTo(municipioDTO2);
        municipioDTO1.setId(null);
        assertThat(municipioDTO1).isNotEqualTo(municipioDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(municipioMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(municipioMapper.fromId(null)).isNull();
    }
}
