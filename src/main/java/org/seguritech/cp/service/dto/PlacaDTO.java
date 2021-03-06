package org.seguritech.cp.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PlacaDTO {



    private String placa;
    private long issi;
    private String tipo;
    private String clase;
    private String marca;
    private String modelo;
    private String color;
    private String vin;



    public PlacaDTO()
    {
        this.tipo="";
        this.placa="";
        this.issi=0;

    }
    public PlacaDTO(String tipoBD,String numeroPlaca,long issi)
    {
        this.tipo=tipoBD;
        this.placa=numeroPlaca;
        this.issi=issi;


    }

    public String getPlaca() {
        return placa;
    }


    public String getTipo() {
        return tipo;
    }

    public String getClase() {
        return clase;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public String getColor() {
        return color;
    }

    public String getVin() {
        return vin;
    }
    public void setPlaca(String placa) {
        this.placa = placa;
    }


    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public long getIssi() {
        return issi;
    }

    public void setIssi(long issi) {
        this.issi = issi;
    }

    @Override
    public String toString() {
        return "PlacaDTO{" +
            "placa='" + placa + '\'' +
            ", issi='" + issi + '\'' +
            ", tipo='" + tipo + '\'' +
            ", clase='" + clase + '\'' +
            ", marca='" + marca + '\'' +
            ", modelo='" + modelo + '\'' +
            ", color='" + color + '\'' +
            ", vin='" + vin + '\'' +
            '}';
    }
    public String GetConsulta()
    {
        return "placa='" + placa + '\'' +
            ", issi='" + issi + '\'' +
            ", tipo='" + tipo ;
    }
}
