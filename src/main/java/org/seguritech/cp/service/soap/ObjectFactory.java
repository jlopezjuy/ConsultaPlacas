//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.11
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen.
// Generado el: 2017.12.06 a las 11:56:53 AM CST
//


package org.seguritech.cp.service.soap;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each
 * Java content interface and Java element interface
 * generated in the com.example.demo package.
 * <p>An ObjectFactory allows you to programatically
 * construct new instances of the Java representation
 * for XML content. The Java representation of XML
 * content can consist of schema derived interfaces
 * and classes representing the binding of schema
 * type definitions, element declarations and model
 * groups.  Factory methods for each of these are
 * provided in this class.
 *
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.example.demo
     *
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Autor }
     *
     */
    public Autor createAutor() {
        return new Autor();
    }

    /**
     * Create an instance of {@link AutorResponse }
     *
     */
    public AutorResponse createAutorResponse() {
        return new AutorResponse();
    }

    /**
     * Create an instance of {@link ConsultaBD }
     *
     */
    public ConsultaBD createConsultaBD() {
        return new ConsultaBD();
    }

    /**
     * Create an instance of {@link ConsultaBDResponse }
     *
     */
    public ConsultaBDResponse createConsultaBDResponse() {
        return new ConsultaBDResponse();
    }

}
