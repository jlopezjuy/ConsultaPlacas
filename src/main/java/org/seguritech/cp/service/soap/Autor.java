//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.11
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen.
// Generado el: 2017.12.06 a las 11:56:53 AM CST
//


package org.seguritech.cp.service.soap;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para anonymous complex type.
 *
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 *
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "Autor")
public class Autor {


    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PlacaResponsePadronDTO {
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
}
