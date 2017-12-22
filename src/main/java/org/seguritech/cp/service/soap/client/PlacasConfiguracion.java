package org.seguritech.cp.service.soap.client;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class PlacasConfiguracion {

	@Bean
	public Jaxb2Marshaller marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setContextPath("org.seguritech.cp.service.soap");
		return marshaller;
	}

	@Bean
	public ClientWebService clientWebService(Jaxb2Marshaller marshaller) {
		/*Properties p = new Properties();
    	try {
    		p.load(new FileReader("aplication.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		ClientWebService client = new ClientWebService();
		//client.setDefaultUri(p.getProperty("endPoint"));
		client.setDefaultUri("http://HYADOCANONB:8090/");
		//client.setDefaultUri("http://HYADOCANONB:9100/");
		//client.setDefaultUri("http://10.16.161.16/WebServiceCBD/ConsultaBDC5.asmx?WSDL");
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		return client;
	}

}
