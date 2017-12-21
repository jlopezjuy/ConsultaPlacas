package org.seguritech.cp.service.soap.client;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import org.seguritech.cp.service.soap.ConsultaBD;
import org.seguritech.cp.service.soap.ConsultaBDResponse;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ClientWebService extends WebServiceGatewaySupport {
	private static final Logger log = LoggerFactory.getLogger(ClientWebService.class);


	public ConsultaBDResponse getConsultaDBResponse(String code,String placa,String tipo) {
		/*Properties p = new Properties();
    	try {
			p.load(new FileReader("aplication.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/


		ConsultaBD request = new ConsultaBD();
		request.setCode(code);
		request.setPlaca(placa);
		request.setTipo(tipo);

		log.info("Requesting data for plata : " + placa);
		ConsultaBDResponse response = (ConsultaBDResponse) getWebServiceTemplate()
				//.marshalSendAndReceive("http://10.16.161.16/WebServiceCBD/ConsultaBDC5.asmx?WSDL",request,
				//.marshalSendAndReceive(p.getProperty("endPoint"),request,
				.marshalSendAndReceive("http://HYADOCANONB:8090/",request,
				//.marshalSendAndReceive("http://HYADOCANONB:9100/",request,
				new SoapActionCallback("http://localhost/ConsultaBD"));
				//.marshalSendAndReceive(p.getProperty("endPoint"),
						//.marshalSendAndReceive("http://10.16.161.16/WebServiceCBD/ConsultaBDC5.asmx",


		return response;
	}

}
