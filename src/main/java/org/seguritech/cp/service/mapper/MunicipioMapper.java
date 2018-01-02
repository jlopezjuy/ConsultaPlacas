package org.seguritech.cp.service.mapper;

import org.seguritech.cp.domain.*;
import org.seguritech.cp.service.dto.MunicipioDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Municipio and its DTO MunicipioDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MunicipioMapper extends EntityMapper<MunicipioDTO, Municipio> {

    

    

    default Municipio fromId(Long id) {
        if (id == null) {
            return null;
        }
        Municipio municipio = new Municipio();
        municipio.setId(id);
        return municipio;
    }
}
