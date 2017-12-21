package org.seguritech.cp.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PlacaResponseArDTO {
	@JsonProperty("Placa")
	private String Placa;
	@JsonProperty("Niv")
	private String Niv;
	@JsonProperty("Averiguacion")
	private String Averiguacion;

	public PlacaResponseArDTO()
	{
		this.Placa="";
		this.Niv="";
		this.Averiguacion="";
	}

	public PlacaResponseArDTO(String placa,String niv,String ave)
	{
		this.Placa=placa;
		this.Niv=niv;
		this.Averiguacion=ave;
	}



	@Override
	public String toString() {
		return "PlacaResponseAR [Placa=" + Placa + ", Niv=" + Niv + ", Averiguacion=" + Averiguacion + "]";
	}
	public String getPlaca() {
		return Placa;
	}
	public void setPlaca(String placa) {
		Placa = placa;
	}
	public String getNiv() {
		return Niv;
	}
	public void setNiv(String niv) {
		Niv = niv;
	}
	public String getAveriguacion() {
		return Averiguacion;
	}
	public void setAveriguacion(String averiguacion) {
		Averiguacion = averiguacion;
	}


}
