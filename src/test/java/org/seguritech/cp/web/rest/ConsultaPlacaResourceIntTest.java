package org.seguritech.cp.web.rest;

import org.seguritech.cp.ConsultaPlacasApp;

import org.seguritech.cp.domain.ConsultaPlaca;
import org.seguritech.cp.domain.Radio;
import org.seguritech.cp.repository.ConsultaPlacaRepository;
import org.seguritech.cp.service.ConsultaPlacaService;
import org.seguritech.cp.service.RadioService;
import org.seguritech.cp.service.dto.ConsultaPlacaDTO;
import org.seguritech.cp.service.mapper.ConsultaPlacaMapper;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static org.seguritech.cp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ConsultaPlacaResource REST controller.
 *
 * @see ConsultaPlacaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConsultaPlacasApp.class)
public class ConsultaPlacaResourceIntTest {

    private static final LocalDateTime DEFAULT_FECHA = LocalDateTime.now();
    private static final LocalDateTime UPDATED_FECHA = LocalDateTime.now(ZoneId.systemDefault());

    private static final String DEFAULT_CONSULTA = "AAAAAAAAAA";
    private static final String UPDATED_CONSULTA = "BBBBBBBBBB";

    private static final String DEFAULT_METODO = "AAAAAAAAAA";
    private static final String UPDATED_METODO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ESTADO = false;
    private static final Boolean UPDATED_ESTADO = true;

    private static final String DEFAULT_RESULTADO = "AAAAAAAAAA";
    private static final String UPDATED_RESULTADO = "BBBBBBBBBB";

    private static final String DEFAULT_COORDENADAS = "AAAAAAAAAA";
    private static final String UPDATED_COORDENADAS = "BBBBBBBBBB";

    @Autowired
    private ConsultaPlacaRepository consultaPlacaRepository;

    @Autowired
    private ConsultaPlacaMapper consultaPlacaMapper;

    @Autowired
    private ConsultaPlacaService consultaPlacaService;


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

    private MockMvc restConsultaPlacaMockMvc;

    private ConsultaPlaca consultaPlaca;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ConsultaPlacaResource consultaPlacaResource = new ConsultaPlacaResource(consultaPlacaService, radioService);
        this.restConsultaPlacaMockMvc = MockMvcBuilders.standaloneSetup(consultaPlacaResource)
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
    public static ConsultaPlaca createEntity(EntityManager em) {
        ConsultaPlaca consultaPlaca = new ConsultaPlaca()
            .fecha(DEFAULT_FECHA)
            .consulta(DEFAULT_CONSULTA)
            .metodo(DEFAULT_METODO)
            .estado(DEFAULT_ESTADO)
            .resultado(DEFAULT_RESULTADO)
            .coordenadas(DEFAULT_COORDENADAS);
        // Add required entity
        Radio radio = RadioResourceIntTest.createEntity(em);
        em.persist(radio);
        em.flush();
        consultaPlaca.setRadio(radio);
        return consultaPlaca;
    }

    @Before
    public void initTest() {
        consultaPlaca = createEntity(em);
    }

    @Test
    @Transactional
    public void createConsultaPlaca() throws Exception {
        int databaseSizeBeforeCreate = consultaPlacaRepository.findAll().size();

        // Create the ConsultaPlaca
        ConsultaPlacaDTO consultaPlacaDTO = consultaPlacaMapper.toDto(consultaPlaca);
        restConsultaPlacaMockMvc.perform(post("/api/consulta-placas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consultaPlacaDTO)))
            .andExpect(status().isCreated());

        // Validate the ConsultaPlaca in the database
        List<ConsultaPlaca> consultaPlacaList = consultaPlacaRepository.findAll();
        assertThat(consultaPlacaList).hasSize(databaseSizeBeforeCreate + 1);
        ConsultaPlaca testConsultaPlaca = consultaPlacaList.get(consultaPlacaList.size() - 1);
        assertThat(testConsultaPlaca.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testConsultaPlaca.getConsulta()).isEqualTo(DEFAULT_CONSULTA);
        assertThat(testConsultaPlaca.getMetodo()).isEqualTo(DEFAULT_METODO);
        assertThat(testConsultaPlaca.isEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testConsultaPlaca.getResultado()).isEqualTo(DEFAULT_RESULTADO);
        assertThat(testConsultaPlaca.getCoordenadas()).isEqualTo(DEFAULT_COORDENADAS);
    }

    @Test
    @Transactional
    public void createConsultaPlacaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = consultaPlacaRepository.findAll().size();

        // Create the ConsultaPlaca with an existing ID
        consultaPlaca.setId(1L);
        ConsultaPlacaDTO consultaPlacaDTO = consultaPlacaMapper.toDto(consultaPlaca);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConsultaPlacaMockMvc.perform(post("/api/consulta-placas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consultaPlacaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ConsultaPlaca in the database
        List<ConsultaPlaca> consultaPlacaList = consultaPlacaRepository.findAll();
        assertThat(consultaPlacaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFechaIsRequired() throws Exception {
        int databaseSizeBeforeTest = consultaPlacaRepository.findAll().size();
        // set the field null
        consultaPlaca.setFecha(null);

        // Create the ConsultaPlaca, which fails.
        ConsultaPlacaDTO consultaPlacaDTO = consultaPlacaMapper.toDto(consultaPlaca);

        restConsultaPlacaMockMvc.perform(post("/api/consulta-placas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consultaPlacaDTO)))
            .andExpect(status().isBadRequest());

        List<ConsultaPlaca> consultaPlacaList = consultaPlacaRepository.findAll();
        assertThat(consultaPlacaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkConsultaIsRequired() throws Exception {
        int databaseSizeBeforeTest = consultaPlacaRepository.findAll().size();
        // set the field null
        consultaPlaca.setConsulta(null);

        // Create the ConsultaPlaca, which fails.
        ConsultaPlacaDTO consultaPlacaDTO = consultaPlacaMapper.toDto(consultaPlaca);

        restConsultaPlacaMockMvc.perform(post("/api/consulta-placas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consultaPlacaDTO)))
            .andExpect(status().isBadRequest());

        List<ConsultaPlaca> consultaPlacaList = consultaPlacaRepository.findAll();
        assertThat(consultaPlacaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMetodoIsRequired() throws Exception {
        int databaseSizeBeforeTest = consultaPlacaRepository.findAll().size();
        // set the field null
        consultaPlaca.setMetodo(null);

        // Create the ConsultaPlaca, which fails.
        ConsultaPlacaDTO consultaPlacaDTO = consultaPlacaMapper.toDto(consultaPlaca);

        restConsultaPlacaMockMvc.perform(post("/api/consulta-placas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consultaPlacaDTO)))
            .andExpect(status().isBadRequest());

        List<ConsultaPlaca> consultaPlacaList = consultaPlacaRepository.findAll();
        assertThat(consultaPlacaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEstadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = consultaPlacaRepository.findAll().size();
        // set the field null
        consultaPlaca.setEstado(null);

        // Create the ConsultaPlaca, which fails.
        ConsultaPlacaDTO consultaPlacaDTO = consultaPlacaMapper.toDto(consultaPlaca);

        restConsultaPlacaMockMvc.perform(post("/api/consulta-placas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consultaPlacaDTO)))
            .andExpect(status().isBadRequest());

        List<ConsultaPlaca> consultaPlacaList = consultaPlacaRepository.findAll();
        assertThat(consultaPlacaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkResultadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = consultaPlacaRepository.findAll().size();
        // set the field null
        consultaPlaca.setResultado(null);

        // Create the ConsultaPlaca, which fails.
        ConsultaPlacaDTO consultaPlacaDTO = consultaPlacaMapper.toDto(consultaPlaca);

        restConsultaPlacaMockMvc.perform(post("/api/consulta-placas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consultaPlacaDTO)))
            .andExpect(status().isBadRequest());

        List<ConsultaPlaca> consultaPlacaList = consultaPlacaRepository.findAll();
        assertThat(consultaPlacaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCoordenadasIsRequired() throws Exception {
        int databaseSizeBeforeTest = consultaPlacaRepository.findAll().size();
        // set the field null
        consultaPlaca.setCoordenadas(null);

        // Create the ConsultaPlaca, which fails.
        ConsultaPlacaDTO consultaPlacaDTO = consultaPlacaMapper.toDto(consultaPlaca);

        restConsultaPlacaMockMvc.perform(post("/api/consulta-placas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consultaPlacaDTO)))
            .andExpect(status().isBadRequest());

        List<ConsultaPlaca> consultaPlacaList = consultaPlacaRepository.findAll();
        assertThat(consultaPlacaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllConsultaPlacas() throws Exception {
        // Initialize the database
        consultaPlacaRepository.saveAndFlush(consultaPlaca);

        // Get all the consultaPlacaList
        restConsultaPlacaMockMvc.perform(get("/api/consulta-placas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(consultaPlaca.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].consulta").value(hasItem(DEFAULT_CONSULTA.toString())))
            .andExpect(jsonPath("$.[*].metodo").value(hasItem(DEFAULT_METODO.toString())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.booleanValue())))
            .andExpect(jsonPath("$.[*].resultado").value(hasItem(DEFAULT_RESULTADO.toString())))
            .andExpect(jsonPath("$.[*].coordenadas").value(hasItem(DEFAULT_COORDENADAS.toString())));
    }

    @Test
    @Transactional
    public void getConsultaPlaca() throws Exception {
        // Initialize the database
        consultaPlacaRepository.saveAndFlush(consultaPlaca);

        // Get the consultaPlaca
        restConsultaPlacaMockMvc.perform(get("/api/consulta-placas/{id}", consultaPlaca.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(consultaPlaca.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.consulta").value(DEFAULT_CONSULTA.toString()))
            .andExpect(jsonPath("$.metodo").value(DEFAULT_METODO.toString()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.booleanValue()))
            .andExpect(jsonPath("$.resultado").value(DEFAULT_RESULTADO.toString()))
            .andExpect(jsonPath("$.coordenadas").value(DEFAULT_COORDENADAS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingConsultaPlaca() throws Exception {
        // Get the consultaPlaca
        restConsultaPlacaMockMvc.perform(get("/api/consulta-placas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConsultaPlaca() throws Exception {
        // Initialize the database
        consultaPlacaRepository.saveAndFlush(consultaPlaca);
        int databaseSizeBeforeUpdate = consultaPlacaRepository.findAll().size();

        // Update the consultaPlaca
        ConsultaPlaca updatedConsultaPlaca = consultaPlacaRepository.findOne(consultaPlaca.getId());
        // Disconnect from session so that the updates on updatedConsultaPlaca are not directly saved in db
        em.detach(updatedConsultaPlaca);
        updatedConsultaPlaca
            .fecha(UPDATED_FECHA)
            .consulta(UPDATED_CONSULTA)
            .metodo(UPDATED_METODO)
            .estado(UPDATED_ESTADO)
            .resultado(UPDATED_RESULTADO)
            .coordenadas(UPDATED_COORDENADAS);
        ConsultaPlacaDTO consultaPlacaDTO = consultaPlacaMapper.toDto(updatedConsultaPlaca);

        restConsultaPlacaMockMvc.perform(put("/api/consulta-placas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consultaPlacaDTO)))
            .andExpect(status().isOk());

        // Validate the ConsultaPlaca in the database
        List<ConsultaPlaca> consultaPlacaList = consultaPlacaRepository.findAll();
        assertThat(consultaPlacaList).hasSize(databaseSizeBeforeUpdate);
        ConsultaPlaca testConsultaPlaca = consultaPlacaList.get(consultaPlacaList.size() - 1);
        assertThat(testConsultaPlaca.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testConsultaPlaca.getConsulta()).isEqualTo(UPDATED_CONSULTA);
        assertThat(testConsultaPlaca.getMetodo()).isEqualTo(UPDATED_METODO);
        assertThat(testConsultaPlaca.isEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testConsultaPlaca.getResultado()).isEqualTo(UPDATED_RESULTADO);
        assertThat(testConsultaPlaca.getCoordenadas()).isEqualTo(UPDATED_COORDENADAS);
    }

    @Test
    @Transactional
    public void updateNonExistingConsultaPlaca() throws Exception {
        int databaseSizeBeforeUpdate = consultaPlacaRepository.findAll().size();

        // Create the ConsultaPlaca
        ConsultaPlacaDTO consultaPlacaDTO = consultaPlacaMapper.toDto(consultaPlaca);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restConsultaPlacaMockMvc.perform(put("/api/consulta-placas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consultaPlacaDTO)))
            .andExpect(status().isCreated());

        // Validate the ConsultaPlaca in the database
        List<ConsultaPlaca> consultaPlacaList = consultaPlacaRepository.findAll();
        assertThat(consultaPlacaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteConsultaPlaca() throws Exception {
        // Initialize the database
        consultaPlacaRepository.saveAndFlush(consultaPlaca);
        int databaseSizeBeforeDelete = consultaPlacaRepository.findAll().size();

        // Get the consultaPlaca
        restConsultaPlacaMockMvc.perform(delete("/api/consulta-placas/{id}", consultaPlaca.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ConsultaPlaca> consultaPlacaList = consultaPlacaRepository.findAll();
        assertThat(consultaPlacaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConsultaPlaca.class);
        ConsultaPlaca consultaPlaca1 = new ConsultaPlaca();
        consultaPlaca1.setId(1L);
        ConsultaPlaca consultaPlaca2 = new ConsultaPlaca();
        consultaPlaca2.setId(consultaPlaca1.getId());
        assertThat(consultaPlaca1).isEqualTo(consultaPlaca2);
        consultaPlaca2.setId(2L);
        assertThat(consultaPlaca1).isNotEqualTo(consultaPlaca2);
        consultaPlaca1.setId(null);
        assertThat(consultaPlaca1).isNotEqualTo(consultaPlaca2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConsultaPlacaDTO.class);
        ConsultaPlacaDTO consultaPlacaDTO1 = new ConsultaPlacaDTO();
        consultaPlacaDTO1.setId(1L);
        ConsultaPlacaDTO consultaPlacaDTO2 = new ConsultaPlacaDTO();
        assertThat(consultaPlacaDTO1).isNotEqualTo(consultaPlacaDTO2);
        consultaPlacaDTO2.setId(consultaPlacaDTO1.getId());
        assertThat(consultaPlacaDTO1).isEqualTo(consultaPlacaDTO2);
        consultaPlacaDTO2.setId(2L);
        assertThat(consultaPlacaDTO1).isNotEqualTo(consultaPlacaDTO2);
        consultaPlacaDTO1.setId(null);
        assertThat(consultaPlacaDTO1).isNotEqualTo(consultaPlacaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(consultaPlacaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(consultaPlacaMapper.fromId(null)).isNull();
    }
}
