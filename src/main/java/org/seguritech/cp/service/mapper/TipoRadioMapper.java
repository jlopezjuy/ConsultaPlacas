package org.seguritech.cp.service.mapper;

import org.seguritech.cp.domain.*;
import org.seguritech.cp.service.dto.TipoRadioDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TipoRadio and its DTO TipoRadioDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TipoRadioMapper extends EntityMapper<TipoRadioDTO, TipoRadio> {

    

    

    default TipoRadio fromId(Long id) {
        if (id == null) {
            return null;
        }
        TipoRadio tipoRadio = new TipoRadio();
        tipoRadio.setId(id);
        return tipoRadio;
    }
}
