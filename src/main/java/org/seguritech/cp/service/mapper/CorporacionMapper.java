package org.seguritech.cp.service.mapper;

import org.seguritech.cp.domain.*;
import org.seguritech.cp.service.dto.CorporacionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Corporacion and its DTO CorporacionDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CorporacionMapper extends EntityMapper<CorporacionDTO, Corporacion> {

    

    

    default Corporacion fromId(Long id) {
        if (id == null) {
            return null;
        }
        Corporacion corporacion = new Corporacion();
        corporacion.setId(id);
        return corporacion;
    }
}
