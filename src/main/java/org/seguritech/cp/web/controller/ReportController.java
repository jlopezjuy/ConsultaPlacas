package org.seguritech.cp.web.controller;

import com.codahale.metrics.annotation.Timed;
import net.bytebuddy.asm.Advice;
import org.seguritech.cp.service.ConsultaPlacaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.jasperreports.JasperReportsMultiFormatView;
import org.springframework.web.servlet.view.jasperreports.JasperReportsPdfView;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ReportController {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private ApplicationContext context;

    @Autowired
    private ConsultaPlacaService consultaPlacaService;

    @GetMapping("/consulta-placas/reporte")
    @Timed
    public ModelAndView report() {

        JasperReportsPdfView view = new JasperReportsPdfView();
        view.setUrl("classpath:reporte_cp.jrxml");
        view.setApplicationContext(context);

        Map<String, Object> params = new HashMap<>();
        params.put("datasource", consultaPlacaService.findAll());

        return new ModelAndView(view, params);
    }

    @GetMapping("/consulta-placas/reporte/{tipo}")
    @Timed
    public ModelAndView dinamycReport(@PathVariable String tipo,
                                      @RequestParam(required=false) String issi,
                                      @RequestParam(required=false) String municipio,
                                      @RequestParam(required=false) String corporacion,
                                      @RequestParam(required=false) String estado,
                                      @RequestParam(required=false) String desde,
                                      @RequestParam(required=false) String hasta) {
        return consultaPlacaService.getReportByType(tipo, issi , municipio, corporacion, estado, desde, hasta);
    }
}
