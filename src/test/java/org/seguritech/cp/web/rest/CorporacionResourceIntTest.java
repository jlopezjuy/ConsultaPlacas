package org.seguritech.cp.web.rest;

import org.seguritech.cp.ConsultaPlacasApp;

import org.seguritech.cp.domain.Corporacion;
import org.seguritech.cp.repository.CorporacionRepository;
import org.seguritech.cp.service.CorporacionService;
import org.seguritech.cp.service.dto.CorporacionDTO;
import org.seguritech.cp.service.mapper.CorporacionMapper;
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
 * Test class for the CorporacionResource REST controller.
 *
 * @see CorporacionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConsultaPlacasApp.class)
public class CorporacionResourceIntTest {

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    @Autowired
    private CorporacionRepository corporacionRepository;

    @Autowired
    private CorporacionMapper corporacionMapper;

    @Autowired
    private CorporacionService corporacionService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCorporacionMockMvc;

    private Corporacion corporacion;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CorporacionResource corporacionResource = new CorporacionResource(corporacionService);
        this.restCorporacionMockMvc = MockMvcBuilders.standaloneSetup(corporacionResource)
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
    public static Corporacion createEntity(EntityManager em) {
        Corporacion corporacion = new Corporacion()
            .descripcion(DEFAULT_DESCRIPCION);
        return corporacion;
    }

    @Before
    public void initTest() {
        corporacion = createEntity(em);
    }

    @Test
    @Transactional
    public void createCorporacion() throws Exception {
        int databaseSizeBeforeCreate = corporacionRepository.findAll().size();

        // Create the Corporacion
        CorporacionDTO corporacionDTO = corporacionMapper.toDto(corporacion);
        restCorporacionMockMvc.perform(post("/api/corporacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corporacionDTO)))
            .andExpect(status().isCreated());

        // Validate the Corporacion in the database
        List<Corporacion> corporacionList = corporacionRepository.findAll();
        assertThat(corporacionList).hasSize(databaseSizeBeforeCreate + 1);
        Corporacion testCorporacion = corporacionList.get(corporacionList.size() - 1);
        assertThat(testCorporacion.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    public void createCorporacionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = corporacionRepository.findAll().size();

        // Create the Corporacion with an existing ID
        corporacion.setId(1L);
        CorporacionDTO corporacionDTO = corporacionMapper.toDto(corporacion);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCorporacionMockMvc.perform(post("/api/corporacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corporacionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Corporacion in the database
        List<Corporacion> corporacionList = corporacionRepository.findAll();
        assertThat(corporacionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDescripcionIsRequired() throws Exception {
        int databaseSizeBeforeTest = corporacionRepository.findAll().size();
        // set the field null
        corporacion.setDescripcion(null);

        // Create the Corporacion, which fails.
        CorporacionDTO corporacionDTO = corporacionMapper.toDto(corporacion);

        restCorporacionMockMvc.perform(post("/api/corporacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corporacionDTO)))
            .andExpect(status().isBadRequest());

        List<Corporacion> corporacionList = corporacionRepository.findAll();
        assertThat(corporacionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCorporacions() throws Exception {
        // Initialize the database
        corporacionRepository.saveAndFlush(corporacion);

        // Get all the corporacionList
        restCorporacionMockMvc.perform(get("/api/corporacions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(corporacion.getId().intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())));
    }

    @Test
    @Transactional
    public void getCorporacion() throws Exception {
        // Initialize the database
        corporacionRepository.saveAndFlush(corporacion);

        // Get the corporacion
        restCorporacionMockMvc.perform(get("/api/corporacions/{id}", corporacion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(corporacion.getId().intValue()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCorporacion() throws Exception {
        // Get the corporacion
        restCorporacionMockMvc.perform(get("/api/corporacions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCorporacion() throws Exception {
        // Initialize the database
        corporacionRepository.saveAndFlush(corporacion);
        int databaseSizeBeforeUpdate = corporacionRepository.findAll().size();

        // Update the corporacion
        Corporacion updatedCorporacion = corporacionRepository.findOne(corporacion.getId());
        // Disconnect from session so that the updates on updatedCorporacion are not directly saved in db
        em.detach(updatedCorporacion);
        updatedCorporacion
            .descripcion(UPDATED_DESCRIPCION);
        CorporacionDTO corporacionDTO = corporacionMapper.toDto(updatedCorporacion);

        restCorporacionMockMvc.perform(put("/api/corporacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corporacionDTO)))
            .andExpect(status().isOk());

        // Validate the Corporacion in the database
        List<Corporacion> corporacionList = corporacionRepository.findAll();
        assertThat(corporacionList).hasSize(databaseSizeBeforeUpdate);
        Corporacion testCorporacion = corporacionList.get(corporacionList.size() - 1);
        assertThat(testCorporacion.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void updateNonExistingCorporacion() throws Exception {
        int databaseSizeBeforeUpdate = corporacionRepository.findAll().size();

        // Create the Corporacion
        CorporacionDTO corporacionDTO = corporacionMapper.toDto(corporacion);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCorporacionMockMvc.perform(put("/api/corporacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corporacionDTO)))
            .andExpect(status().isCreated());

        // Validate the Corporacion in the database
        List<Corporacion> corporacionList = corporacionRepository.findAll();
        assertThat(corporacionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCorporacion() throws Exception {
        // Initialize the database
        corporacionRepository.saveAndFlush(corporacion);
        int databaseSizeBeforeDelete = corporacionRepository.findAll().size();

        // Get the corporacion
        restCorporacionMockMvc.perform(delete("/api/corporacions/{id}", corporacion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Corporacion> corporacionList = corporacionRepository.findAll();
        assertThat(corporacionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Corporacion.class);
        Corporacion corporacion1 = new Corporacion();
        corporacion1.setId(1L);
        Corporacion corporacion2 = new Corporacion();
        corporacion2.setId(corporacion1.getId());
        assertThat(corporacion1).isEqualTo(corporacion2);
        corporacion2.setId(2L);
        assertThat(corporacion1).isNotEqualTo(corporacion2);
        corporacion1.setId(null);
        assertThat(corporacion1).isNotEqualTo(corporacion2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CorporacionDTO.class);
        CorporacionDTO corporacionDTO1 = new CorporacionDTO();
        corporacionDTO1.setId(1L);
        CorporacionDTO corporacionDTO2 = new CorporacionDTO();
        assertThat(corporacionDTO1).isNotEqualTo(corporacionDTO2);
        corporacionDTO2.setId(corporacionDTO1.getId());
        assertThat(corporacionDTO1).isEqualTo(corporacionDTO2);
        corporacionDTO2.setId(2L);
        assertThat(corporacionDTO1).isNotEqualTo(corporacionDTO2);
        corporacionDTO1.setId(null);
        assertThat(corporacionDTO1).isNotEqualTo(corporacionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(corporacionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(corporacionMapper.fromId(null)).isNull();
    }
}
