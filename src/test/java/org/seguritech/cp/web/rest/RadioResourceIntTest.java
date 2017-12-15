package org.seguritech.cp.web.rest;

import org.seguritech.cp.ConsultaPlacasApp;

import org.seguritech.cp.domain.Radio;
import org.seguritech.cp.domain.Marca;
import org.seguritech.cp.domain.Municipio;
import org.seguritech.cp.domain.Corporacion;
import org.seguritech.cp.domain.TipoRadio;
import org.seguritech.cp.repository.RadioRepository;
import org.seguritech.cp.service.RadioService;
import org.seguritech.cp.service.dto.RadioDTO;
import org.seguritech.cp.service.mapper.RadioMapper;
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

import org.seguritech.cp.domain.enumeration.Permiso;
/**
 * Test class for the RadioResource REST controller.
 *
 * @see RadioResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConsultaPlacasApp.class)
public class RadioResourceIntTest {

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final Permiso DEFAULT_PERMISO = Permiso.AUTOS_ROBADOS;
    private static final Permiso UPDATED_PERMISO = Permiso.PADRON_VEHICULAR;

    private static final String DEFAULT_ID_RADIO = "AAAAAAAAAA";
    private static final String UPDATED_ID_RADIO = "BBBBBBBBBB";

    @Autowired
    private RadioRepository radioRepository;

    @Autowired
    private RadioMapper radioMapper;

    @Autowired
    private RadioService radioService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRadioMockMvc;

    private Radio radio;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RadioResource radioResource = new RadioResource(radioService);
        this.restRadioMockMvc = MockMvcBuilders.standaloneSetup(radioResource)
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
    public static Radio createEntity(EntityManager em) {
        Radio radio = new Radio()
            .descripcion(DEFAULT_DESCRIPCION)
            .permiso(DEFAULT_PERMISO)
            .idRadio(DEFAULT_ID_RADIO);
        // Add required entity
        Marca marca = MarcaResourceIntTest.createEntity(em);
        em.persist(marca);
        em.flush();
        radio.setMarca(marca);
        // Add required entity
        Municipio municipio = MunicipioResourceIntTest.createEntity(em);
        em.persist(municipio);
        em.flush();
        radio.setMunicipio(municipio);
        // Add required entity
        Corporacion corporacion = CorporacionResourceIntTest.createEntity(em);
        em.persist(corporacion);
        em.flush();
        radio.setCorporacion(corporacion);
        // Add required entity
        TipoRadio tipoRadio = TipoRadioResourceIntTest.createEntity(em);
        em.persist(tipoRadio);
        em.flush();
        radio.setTipoRadio(tipoRadio);
        return radio;
    }

    @Before
    public void initTest() {
        radio = createEntity(em);
    }

    @Test
    @Transactional
    public void createRadio() throws Exception {
        int databaseSizeBeforeCreate = radioRepository.findAll().size();

        // Create the Radio
        RadioDTO radioDTO = radioMapper.toDto(radio);
        restRadioMockMvc.perform(post("/api/radios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(radioDTO)))
            .andExpect(status().isCreated());

        // Validate the Radio in the database
        List<Radio> radioList = radioRepository.findAll();
        assertThat(radioList).hasSize(databaseSizeBeforeCreate + 1);
        Radio testRadio = radioList.get(radioList.size() - 1);
        assertThat(testRadio.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testRadio.getPermiso()).isEqualTo(DEFAULT_PERMISO);
        assertThat(testRadio.getIdRadio()).isEqualTo(DEFAULT_ID_RADIO);
    }

    @Test
    @Transactional
    public void createRadioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = radioRepository.findAll().size();

        // Create the Radio with an existing ID
        radio.setIssi(1L);
        RadioDTO radioDTO = radioMapper.toDto(radio);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRadioMockMvc.perform(post("/api/radios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(radioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Radio in the database
        List<Radio> radioList = radioRepository.findAll();
        assertThat(radioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDescripcionIsRequired() throws Exception {
        int databaseSizeBeforeTest = radioRepository.findAll().size();
        // set the field null
        radio.setDescripcion(null);

        // Create the Radio, which fails.
        RadioDTO radioDTO = radioMapper.toDto(radio);

        restRadioMockMvc.perform(post("/api/radios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(radioDTO)))
            .andExpect(status().isBadRequest());

        List<Radio> radioList = radioRepository.findAll();
        assertThat(radioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIdRadioIsRequired() throws Exception {
        int databaseSizeBeforeTest = radioRepository.findAll().size();
        // set the field null
        radio.setIdRadio(null);

        // Create the Radio, which fails.
        RadioDTO radioDTO = radioMapper.toDto(radio);

        restRadioMockMvc.perform(post("/api/radios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(radioDTO)))
            .andExpect(status().isBadRequest());

        List<Radio> radioList = radioRepository.findAll();
        assertThat(radioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRadios() throws Exception {
        // Initialize the database
        radioRepository.saveAndFlush(radio);

        // Get all the radioList
        restRadioMockMvc.perform(get("/api/radios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].issi").value(hasItem(radio.getIssi().intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].permiso").value(hasItem(DEFAULT_PERMISO.toString())))
            .andExpect(jsonPath("$.[*].idRadio").value(hasItem(DEFAULT_ID_RADIO.toString())));
    }

    @Test
    @Transactional
    public void getRadio() throws Exception {
        // Initialize the database
        radioRepository.saveAndFlush(radio);

        // Get the radio
        restRadioMockMvc.perform(get("/api/radios/{issi}", radio.getIssi()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.issi").value(radio.getIssi().intValue()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.permiso").value(DEFAULT_PERMISO.toString()))
            .andExpect(jsonPath("$.idRadio").value(DEFAULT_ID_RADIO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRadio() throws Exception {
        // Get the radio
        restRadioMockMvc.perform(get("/api/radios/{issi}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRadio() throws Exception {
        // Initialize the database
        radioRepository.saveAndFlush(radio);
        int databaseSizeBeforeUpdate = radioRepository.findAll().size();

        // Update the radio
        Radio updatedRadio = radioRepository.findOne(radio.getIssi());
        // Disconnect from session so that the updates on updatedRadio are not directly saved in db
        em.detach(updatedRadio);
        updatedRadio
            .descripcion(UPDATED_DESCRIPCION)
            .permiso(UPDATED_PERMISO)
            .idRadio(UPDATED_ID_RADIO);
        RadioDTO radioDTO = radioMapper.toDto(updatedRadio);

        restRadioMockMvc.perform(put("/api/radios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(radioDTO)))
            .andExpect(status().isOk());

        // Validate the Radio in the database
        List<Radio> radioList = radioRepository.findAll();
        assertThat(radioList).hasSize(databaseSizeBeforeUpdate);
        Radio testRadio = radioList.get(radioList.size() - 1);
        assertThat(testRadio.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testRadio.getPermiso()).isEqualTo(UPDATED_PERMISO);
        assertThat(testRadio.getIdRadio()).isEqualTo(UPDATED_ID_RADIO);
    }

    @Test
    @Transactional
    public void updateNonExistingRadio() throws Exception {
        int databaseSizeBeforeUpdate = radioRepository.findAll().size();

        // Create the Radio
        RadioDTO radioDTO = radioMapper.toDto(radio);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRadioMockMvc.perform(put("/api/radios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(radioDTO)))
            .andExpect(status().isCreated());

        // Validate the Radio in the database
        List<Radio> radioList = radioRepository.findAll();
        assertThat(radioList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRadio() throws Exception {
        // Initialize the database
        radioRepository.saveAndFlush(radio);
        int databaseSizeBeforeDelete = radioRepository.findAll().size();

        // Get the radio
        restRadioMockMvc.perform(delete("/api/radios/{issi}", radio.getIssi())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Radio> radioList = radioRepository.findAll();
        assertThat(radioList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Radio.class);
        Radio radio1 = new Radio();
        radio1.setIssi(1L);
        Radio radio2 = new Radio();
        radio2.setIssi(radio1.getIssi());
        assertThat(radio1).isEqualTo(radio2);
        radio2.setIssi(2L);
        assertThat(radio1).isNotEqualTo(radio2);
        radio1.setIssi(null);
        assertThat(radio1).isNotEqualTo(radio2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RadioDTO.class);
        RadioDTO radioDTO1 = new RadioDTO();
        radioDTO1.setIssi(1L);
        RadioDTO radioDTO2 = new RadioDTO();
        assertThat(radioDTO1).isNotEqualTo(radioDTO2);
        radioDTO2.setIssi(radioDTO1.getIssi());
        assertThat(radioDTO1).isEqualTo(radioDTO2);
        radioDTO2.setIssi(2L);
        assertThat(radioDTO1).isNotEqualTo(radioDTO2);
        radioDTO1.setIssi(null);
        assertThat(radioDTO1).isNotEqualTo(radioDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(radioMapper.fromId(42L).getIssi()).isEqualTo(42);
        assertThat(radioMapper.fromId(null)).isNull();
    }
}
