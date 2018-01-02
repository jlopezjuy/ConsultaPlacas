package org.seguritech.cp.web.rest;

import org.seguritech.cp.ConsultaPlacasApp;

import org.seguritech.cp.domain.Marca;
import org.seguritech.cp.repository.MarcaRepository;
import org.seguritech.cp.service.MarcaService;
import org.seguritech.cp.service.dto.MarcaDTO;
import org.seguritech.cp.service.mapper.MarcaMapper;
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
 * Test class for the MarcaResource REST controller.
 *
 * @see MarcaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConsultaPlacasApp.class)
public class MarcaResourceIntTest {

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    @Autowired
    private MarcaRepository marcaRepository;

    @Autowired
    private MarcaMapper marcaMapper;

    @Autowired
    private MarcaService marcaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMarcaMockMvc;

    private Marca marca;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MarcaResource marcaResource = new MarcaResource(marcaService);
        this.restMarcaMockMvc = MockMvcBuilders.standaloneSetup(marcaResource)
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
    public static Marca createEntity(EntityManager em) {
        Marca marca = new Marca()
            .descripcion(DEFAULT_DESCRIPCION);
        return marca;
    }

    @Before
    public void initTest() {
        marca = createEntity(em);
    }

    @Test
    @Transactional
    public void createMarca() throws Exception {
        int databaseSizeBeforeCreate = marcaRepository.findAll().size();

        // Create the Marca
        MarcaDTO marcaDTO = marcaMapper.toDto(marca);
        restMarcaMockMvc.perform(post("/api/marcas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marcaDTO)))
            .andExpect(status().isCreated());

        // Validate the Marca in the database
        List<Marca> marcaList = marcaRepository.findAll();
        assertThat(marcaList).hasSize(databaseSizeBeforeCreate + 1);
        Marca testMarca = marcaList.get(marcaList.size() - 1);
        assertThat(testMarca.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    public void createMarcaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = marcaRepository.findAll().size();

        // Create the Marca with an existing ID
        marca.setId(1L);
        MarcaDTO marcaDTO = marcaMapper.toDto(marca);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMarcaMockMvc.perform(post("/api/marcas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marcaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Marca in the database
        List<Marca> marcaList = marcaRepository.findAll();
        assertThat(marcaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDescripcionIsRequired() throws Exception {
        int databaseSizeBeforeTest = marcaRepository.findAll().size();
        // set the field null
        marca.setDescripcion(null);

        // Create the Marca, which fails.
        MarcaDTO marcaDTO = marcaMapper.toDto(marca);

        restMarcaMockMvc.perform(post("/api/marcas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marcaDTO)))
            .andExpect(status().isBadRequest());

        List<Marca> marcaList = marcaRepository.findAll();
        assertThat(marcaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMarcas() throws Exception {
        // Initialize the database
        marcaRepository.saveAndFlush(marca);

        // Get all the marcaList
        restMarcaMockMvc.perform(get("/api/marcas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(marca.getId().intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())));
    }

    @Test
    @Transactional
    public void getMarca() throws Exception {
        // Initialize the database
        marcaRepository.saveAndFlush(marca);

        // Get the marca
        restMarcaMockMvc.perform(get("/api/marcas/{id}", marca.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(marca.getId().intValue()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMarca() throws Exception {
        // Get the marca
        restMarcaMockMvc.perform(get("/api/marcas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMarca() throws Exception {
        // Initialize the database
        marcaRepository.saveAndFlush(marca);
        int databaseSizeBeforeUpdate = marcaRepository.findAll().size();

        // Update the marca
        Marca updatedMarca = marcaRepository.findOne(marca.getId());
        // Disconnect from session so that the updates on updatedMarca are not directly saved in db
        em.detach(updatedMarca);
        updatedMarca
            .descripcion(UPDATED_DESCRIPCION);
        MarcaDTO marcaDTO = marcaMapper.toDto(updatedMarca);

        restMarcaMockMvc.perform(put("/api/marcas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marcaDTO)))
            .andExpect(status().isOk());

        // Validate the Marca in the database
        List<Marca> marcaList = marcaRepository.findAll();
        assertThat(marcaList).hasSize(databaseSizeBeforeUpdate);
        Marca testMarca = marcaList.get(marcaList.size() - 1);
        assertThat(testMarca.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void updateNonExistingMarca() throws Exception {
        int databaseSizeBeforeUpdate = marcaRepository.findAll().size();

        // Create the Marca
        MarcaDTO marcaDTO = marcaMapper.toDto(marca);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMarcaMockMvc.perform(put("/api/marcas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marcaDTO)))
            .andExpect(status().isCreated());

        // Validate the Marca in the database
        List<Marca> marcaList = marcaRepository.findAll();
        assertThat(marcaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMarca() throws Exception {
        // Initialize the database
        marcaRepository.saveAndFlush(marca);
        int databaseSizeBeforeDelete = marcaRepository.findAll().size();

        // Get the marca
        restMarcaMockMvc.perform(delete("/api/marcas/{id}", marca.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Marca> marcaList = marcaRepository.findAll();
        assertThat(marcaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Marca.class);
        Marca marca1 = new Marca();
        marca1.setId(1L);
        Marca marca2 = new Marca();
        marca2.setId(marca1.getId());
        assertThat(marca1).isEqualTo(marca2);
        marca2.setId(2L);
        assertThat(marca1).isNotEqualTo(marca2);
        marca1.setId(null);
        assertThat(marca1).isNotEqualTo(marca2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MarcaDTO.class);
        MarcaDTO marcaDTO1 = new MarcaDTO();
        marcaDTO1.setId(1L);
        MarcaDTO marcaDTO2 = new MarcaDTO();
        assertThat(marcaDTO1).isNotEqualTo(marcaDTO2);
        marcaDTO2.setId(marcaDTO1.getId());
        assertThat(marcaDTO1).isEqualTo(marcaDTO2);
        marcaDTO2.setId(2L);
        assertThat(marcaDTO1).isNotEqualTo(marcaDTO2);
        marcaDTO1.setId(null);
        assertThat(marcaDTO1).isNotEqualTo(marcaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(marcaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(marcaMapper.fromId(null)).isNull();
    }
}
