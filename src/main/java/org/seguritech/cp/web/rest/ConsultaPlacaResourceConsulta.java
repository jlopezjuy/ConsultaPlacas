package org.seguritech.cp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.jhipster.web.util.ResponseUtil;
import org.seguritech.cp.domain.enumeration.Permiso;
import org.seguritech.cp.service.ConsultaPlacaService;
import org.seguritech.cp.service.RadioService;
import org.seguritech.cp.service.dto.*;
import org.seguritech.cp.service.soap.ConsultaBDResponse;
import org.seguritech.cp.service.soap.client.ClientWebService;
import org.seguritech.cp.web.rest.errors.BadRequestAlertException;
import org.seguritech.cp.web.rest.util.DateUtil;
import org.seguritech.cp.web.rest.util.HeaderUtil;
import org.seguritech.cp.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ConsultaPlaca.
 */
@RestController
@RequestMapping("/consulta")
public class ConsultaPlacaResourceConsulta {

    private final Logger log = LoggerFactory.getLogger(ConsultaPlacaResourceConsulta.class);

    private static final String ENTITY_NAME = "consultaPlaca";

    private final ConsultaPlacaService consultaPlacaService;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private  ClientWebService clientService;

    private final RadioService radioService;

    public ConsultaPlacaResourceConsulta(ConsultaPlacaService consultaPlacaService, RadioService radioService) {
        this.consultaPlacaService = consultaPlacaService;
        this.radioService = radioService;
    }





    @GetMapping(value = "/search",produces = "text/html")
    @Timed
    public String placa(@RequestParam("query") String placa) {

        System.out.println("Got " + placa);
        PlacaDTO placaObject=null;
        try {
            placaObject = OBJECT_MAPPER.readValue(placa, PlacaDTO.class);
        }
        catch(IOException e){
            System.err.println("No pudo mapear la placa");
        }
        RadioDTO resultRadioDto=radioService.findOne((long) placaObject.getIssi());
        if(resultRadioDto==null)
        {
            guardarConsultaPlacas(placaObject,"La radio con nro de issi: "+placaObject.getIssi()+" no existe en la base de datos de radios",false,false);
            return "La radio con nro de issi "+placaObject.getIssi()+" no existe en la base de datos de radios";
        }
        ConsultaBDResponse response = this.clientService.getConsultaDBResponse("WasConBD",placaObject.getPlaca(),placaObject.getTipo());

        System.out.println(response.getConsultaBDResult());
        String respuestaPadron=response.getConsultaBDResult();
        String respuestaAR=response.getConsultaBDResult();
        if(placaObject.getTipo().trim().equals("Padron"))
        {	//System.out.println(respuestaPadron);
            if(resultRadioDto.getPermiso().compareTo(Permiso.PADRON_VEHICULAR)==0 || resultRadioDto.getPermiso().compareTo(Permiso.AMBOS)==0)
            {

                if (!respuestaPadron.equals("[]"))
                {
                    respuestaPadron = respuestaPadron.replace("]", "");
                    respuestaPadron = respuestaPadron.replace("[", "");

                    PlacaResponsePadronDTO placaObjectResponse = null;
                    try {
                        placaObjectResponse = OBJECT_MAPPER.readValue(respuestaPadron, PlacaResponsePadronDTO.class);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    respuestaPadron = respuestaPadron.replace("{", "");
                    respuestaPadron = respuestaPadron.replace("}", "");
                    respuestaPadron = respuestaPadron.replaceAll("\"", " ");
                    if (placaObjectResponse.getPlaca().toUpperCase().trim().equals(placaObject.getPlaca().toUpperCase().trim()))
                    {
                        guardarConsultaPlacas(placaObject,respuestaPadron,true,true);
                        return respuestaPadron;
                    }
                    else//nunca entra aca solo con mock es necesario.pero si padron cambia y devuelve resultado por patron similar
                    // y no exacto,entonces esta parte si sirve en ese caso hacer lo mismo que con AR EN EL CODIGO DE ABAJO
                    {
                        guardarConsultaPlacas(placaObject,placaObject.getPlaca() + " sin reporte",false,true);
                        return placaObject.getPlaca() + " sin reporte";
                    }
                }
                else
                {
                    guardarConsultaPlacas(placaObject,placaObject.getPlaca() + " sin reporte",false,true);
                    return placaObject.getPlaca() + " sin reporte";
                }

            }
            else
            {   guardarConsultaPlacas(placaObject, "La radio con nro de issi "+resultRadioDto.getIssi() + " no cuenta con permiso de consulta a la base de datos de padron vehicular",false,true);
                return "La radio con nro de issi "+resultRadioDto.getIssi() + " no cuenta con permiso de consulta a la base de datos de padron vehicular";

            }

        }

        if(placaObject.getTipo().trim().equals("AR"))
        {
            if(resultRadioDto.getPermiso().compareTo(Permiso.AUTOS_ROBADOS)==0 || resultRadioDto.getPermiso().compareTo(Permiso.AMBOS)==0)
            {
                if (!respuestaAR.equals("[]")) {
                    ArrayList<PlacaResponseArDTO> placasAR = null;
                    try
                    {
                        placasAR = OBJECT_MAPPER.readValue(respuestaAR, new TypeReference<ArrayList<PlacaResponseArDTO>>() {
                        });
                    }
                    catch (IOException E)
                    {

                    }

                    for (int i = 0; i < placasAR.size(); i++) {
                        if (!placaObject.getPlaca().toUpperCase().equals(placasAR.get(i).getPlaca())) {
                            placasAR.remove(i);
                            i--;
                        }

                    }
                    if (placasAR.size() > 0)
                    {
                        String placasArFinal = placasAR.toString();
                        placasArFinal = placasArFinal.replace("[", "");
                        placasArFinal = placasArFinal.replace("]", "");
                        placasArFinal = placasArFinal.replace("=", ":");
                        placasArFinal = placasArFinal.replace("PlacaResponseAR", "");
                        guardarConsultaPlacas(placaObject,placasArFinal,true,true);
                        return placasArFinal;
                    }
                    else
                    {   guardarConsultaPlacas(placaObject,placaObject.getPlaca() + " sin reportes",false,true);
                        return placaObject.getPlaca() + " sin reportes";
                    }

                }
                else
                {
                    guardarConsultaPlacas(placaObject,placaObject.getPlaca() + " sin reportes",false,true);
                    return placaObject.getPlaca() + " sin reportes";
                }
            }
            else
            {
                guardarConsultaPlacas(placaObject,"La radio con nro de issi "+resultRadioDto.getIssi() + " no cuenta con permiso de consulta a la base de datos de Autos robados",false,true);
                return "La radio con nro de issi "+resultRadioDto.getIssi() + " no cuenta con permiso de consulta a la base de datos de Autos robados";
            }
        }


        return "";
    }


    public void guardarConsultaPlacas(PlacaDTO placaObject,String restResponse,boolean estado,boolean radioExistente)
    {
        ConsultaPlacaDTO consultaPlacaDTO=new ConsultaPlacaDTO();
        consultaPlacaDTO.setFecha(DateUtil.getActualDate());
        consultaPlacaDTO.setConsulta(placaObject.GetConsulta());
        consultaPlacaDTO.setMetodo(placaObject.getTipo());
        consultaPlacaDTO.setEstado(estado);
        consultaPlacaDTO.setResultado(restResponse);
        consultaPlacaDTO.setCoordenadas("10000 20000");
        if(radioExistente)
            consultaPlacaDTO.setRadioIssi(placaObject.getIssi());
        else
            consultaPlacaDTO.setRadioIssi(null);

        ConsultaPlacaDTO result = consultaPlacaService.save(consultaPlacaDTO);
        if(result.getId()!=null)
        {
            log.info("Pudo guardarse la consulta de placa con id: "+result.getId());

        }
        else
        {
            log.error("No Pudo guardarse la consulta de placas ");
        }
    }



    @Bean
    CommandLineRunner lookup(ClientWebService serviceClient) {
        return args -> {
            this.clientService=serviceClient;
            String code = "WasConBD";
            String placa= "Abc123";
            String tipo= "PV";
            if (args.length > 0) {
                code = args[0];
                placa= args[1];
                tipo= args[2];
            }
            ConsultaBDResponse response = serviceClient.getConsultaDBResponse(code,placa,tipo);
        };

    }
}
