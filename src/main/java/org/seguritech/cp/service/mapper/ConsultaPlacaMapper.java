package org.seguritech.cp.service.mapper;

import org.seguritech.cp.domain.*;
import org.seguritech.cp.service.dto.ConsultaPlacaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ConsultaPlaca and its DTO ConsultaPlacaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ConsultaPlacaMapper extends EntityMapper<ConsultaPlacaDTO, ConsultaPlaca> {

    

    

    default ConsultaPlaca fromId(Long id) {
        if (id == null) {
            return null;
        }
        ConsultaPlaca consultaPlaca = new ConsultaPlaca();
        consultaPlaca.setId(id);
        return consultaPlaca;
    }
}
