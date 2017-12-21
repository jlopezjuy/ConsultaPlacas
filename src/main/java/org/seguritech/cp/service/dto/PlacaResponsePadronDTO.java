package org.seguritech.cp.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlacaResponsePadronDTO {
	@JsonProperty("Placa")
	private String Placa;
	@JsonProperty("Clase")
	private String Clase;
	@JsonProperty("Marca")
	private String Marca;
	@JsonProperty("Modelo")
	private String Modelo;
	@JsonProperty("Color")
	private String Color;
	@JsonProperty("VIN")
	private String VIN;

	public PlacaResponsePadronDTO()
	{
		this.Placa="";
		this.Clase="";
		this.Marca="";
		this.Modelo="";
		this.Color="";
		this.VIN="";
	}
	public PlacaResponsePadronDTO(String Placa,String Clase,String Marca,String Modelo,String Color,String VIN)
	{

		this.Placa=Placa;
		this.Clase=Clase;
		this.Marca=Marca;
		this.Modelo=Modelo;
		this.Color=Color;
		this.VIN=VIN;



	}

	public String getPlaca() {
		return Placa;
	}
	public void setPlaca(String placa) {
		Placa = placa;
	}
	public String getClase() {
		return Clase;
	}
	public void setClase(String clase) {
		Clase = clase;
	}
	public String getMarca() {
		return Marca;
	}
	public void setMarca(String marca) {
		Marca = marca;
	}
	public String getModelo() {
		return Modelo;
	}
	public void setModelo(String modelo) {
		Modelo = modelo;
	}
	public String getColor() {
		return Color;
	}
	public void setColor(String color) {
		Color = color;
	}
	public String getVIN() {
		return VIN;
	}
	public void setVIN(String vIN) {
		VIN = vIN;
	}

	@Override
	public String toString() {
		return "PlacaResponsePadron [Placa=" + Placa + ", Clase=" + Clase + ", Marca=" + Marca + ", Modelo=" + Modelo
				+ ", Color=" + Color + ", VIN=" + VIN + "]";
	}



}
