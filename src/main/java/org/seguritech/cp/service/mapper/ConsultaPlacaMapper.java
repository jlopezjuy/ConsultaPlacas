package org.seguritech.cp.service.mapper;

import org.seguritech.cp.domain.*;
import org.seguritech.cp.service.dto.ConsultaPlacaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ConsultaPlaca and its DTO ConsultaPlacaDTO.
 */
@Mapper(componentModel = "spring", uses = {MunicipioMapper.class, CorporacionMapper.class})
public interface ConsultaPlacaMapper extends EntityMapper<ConsultaPlacaDTO, ConsultaPlaca> {

    @Mapping(source = "municipio.id", target = "municipioId")
    @Mapping(source = "municipio.descripcion", target = "municipioDescripcion")
    @Mapping(source = "corporacion.id", target = "corporacionId")
    @Mapping(source = "corporacion.descripcion", target = "corporacionDescripcion")
    ConsultaPlacaDTO toDto(ConsultaPlaca consultaPlaca); 

    @Mapping(source = "municipioId", target = "municipio")
    @Mapping(source = "corporacionId", target = "corporacion")
    ConsultaPlaca toEntity(ConsultaPlacaDTO consultaPlacaDTO);

    default ConsultaPlaca fromId(Long id) {
        if (id == null) {
            return null;
        }
        ConsultaPlaca consultaPlaca = new ConsultaPlaca();
        consultaPlaca.setId(id);
        return consultaPlaca;
    }
}
