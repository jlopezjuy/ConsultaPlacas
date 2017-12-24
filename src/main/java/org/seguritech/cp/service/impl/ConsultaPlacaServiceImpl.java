package org.seguritech.cp.service.impl;

import org.seguritech.cp.service.ConsultaPlacaService;
import org.seguritech.cp.domain.ConsultaPlaca;
import org.seguritech.cp.repository.ConsultaPlacaRepository;
import org.seguritech.cp.service.dto.ConsultaPlacaDTO;
import org.seguritech.cp.service.mapper.ConsultaPlacaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.jasperreports.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Service Implementation for managing ConsultaPlaca.
 */
@Service
@Transactional
public class ConsultaPlacaServiceImpl implements ConsultaPlacaService{

    private final Logger log = LoggerFactory.getLogger(ConsultaPlacaServiceImpl.class);

    private final static String REPORTE_PDF = "PDF";
    private final static String REPORTE_XLS = "XLS";
    private final static String REPORTE_XLSX = "XLSX";
    private final static String REPORTE_CSV = "CSV";

    private final ConsultaPlacaRepository consultaPlacaRepository;

    private final ConsultaPlacaMapper consultaPlacaMapper;

    @Autowired
    private ApplicationContext context;

    public ConsultaPlacaServiceImpl(ConsultaPlacaRepository consultaPlacaRepository, ConsultaPlacaMapper consultaPlacaMapper) {
        this.consultaPlacaRepository = consultaPlacaRepository;
        this.consultaPlacaMapper = consultaPlacaMapper;
    }

    /**
     * Save a consultaPlaca.
     *
     * @param consultaPlacaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ConsultaPlacaDTO save(ConsultaPlacaDTO consultaPlacaDTO) {
        log.debug("Request to save ConsultaPlaca : {}", consultaPlacaDTO);
        ConsultaPlaca consultaPlaca = consultaPlacaMapper.toEntity(consultaPlacaDTO);
        consultaPlaca = consultaPlacaRepository.save(consultaPlaca);
        return consultaPlacaMapper.toDto(consultaPlaca);
    }

    /**
     * Get all the consultaPlacas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ConsultaPlacaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ConsultaPlacas");
        return consultaPlacaRepository.findAll(pageable)
            .map(consultaPlacaMapper::toDto);
    }

    /**
     * Get all the consultaPlacas.
     * @return the list of entities
     */
    @Override
    public List<ConsultaPlacaDTO> findAll() {
        return consultaPlacaMapper.toDto(consultaPlacaRepository.findAll());
    }

    /**
     * Get one consultaPlaca by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ConsultaPlacaDTO findOne(Long id) {
        log.debug("Request to get ConsultaPlaca : {}", id);
        ConsultaPlaca consultaPlaca = consultaPlacaRepository.findOne(id);
        return consultaPlacaMapper.toDto(consultaPlaca);
    }

    /**
     * Delete the consultaPlaca by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ConsultaPlaca : {}", id);
        consultaPlacaRepository.delete(id);
    }

    /**
     *
     * @param type
     * @author jlopez
     * @return the report
     */
    @Override
    public ModelAndView getReportByType(String type) {
        ModelAndView model = null;

        switch (type) {
            case REPORTE_PDF:  model = getPdf();
                break;
            case REPORTE_XLS:  model = getXls();
                break;
            case REPORTE_XLSX:  model = getXlsx();
                break;
            case REPORTE_CSV:  model = getCsv();
                break;

            default: model = getPdf();
                break;
        }

        return model;
    }

    /**
     *
     * @return
     * @author jlopez
     */
    private ModelAndView getPdf(){

        JasperReportsPdfView view = new JasperReportsPdfView();
        view.setUrl("classpath:reporte_cp.jrxml");
        view.setApplicationContext(context);

        Map<String, Object> params = new HashMap<>();
        params.put("datasource", this.findAll());
        return new ModelAndView(view, params);

    }

    /**
     *
     * @return
     * @author jlopez
     */
    private ModelAndView getXls(){

        JasperReportsXlsView view = new JasperReportsXlsView();
        view.setUrl("classpath:reporte_cp.jrxml");
        view.setApplicationContext(context);

        Map<String, Object> params = new HashMap<>();
        params.put("datasource", this.findAll());
        return new ModelAndView(view, params);

    }

    /**
     *
     * @return
     * @author jlopez
     */
    private ModelAndView getXlsx(){

        JasperReportsXlsxView view = new JasperReportsXlsxView();
        view.setUrl("classpath:reporte_cp.jrxml");
        view.setApplicationContext(context);

        Map<String, Object> params = new HashMap<>();
        params.put("datasource", this.findAll());
        return new ModelAndView(view, params);

    }

    /**
     *
     * @return
     * @author jlopez
     */
    private ModelAndView getCsv(){

        JasperReportsCsvView view = new JasperReportsCsvView();

        view.setUrl("classpath:reporte_cp.jrxml");
        view.setApplicationContext(context);

        Map<String, Object> params = new HashMap<>();
        params.put("datasource", this.findAll());
        return new ModelAndView(view, params);

    }

    /**
     *
     * @return
     * @author jlopez
     */
    private ModelAndView getRtf(){

        JasperReportsMultiFormatView view = new JasperReportsMultiFormatView();

        view.setUrl("classpath:reporte_cp.jrxml");
        view.setApplicationContext(context);

        Map<String, Object> params = new HashMap<>();
        params.put("datasource", this.findAll());
        return new ModelAndView(view, params);

    }




}
