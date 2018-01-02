package org.seguritech.cp.service.mapper;

import org.seguritech.cp.domain.*;
import org.seguritech.cp.service.dto.ConsultaPlacaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ConsultaPlaca and its DTO ConsultaPlacaDTO.
 */
@Mapper(componentModel = "spring", uses = {RadioMapper.class})
public interface ConsultaPlacaMapper extends EntityMapper<ConsultaPlacaDTO, ConsultaPlaca> {

    @Mapping(source = "radio.issi", target = "radioIssi")
    @Mapping(source = "radio.descripcion", target = "radioDescripcion")
    @Mapping(source = "radio.responsable", target = "radioResponsable")
    @Mapping(source = "radio.municipio.descripcion", target = "radioMunicipio")
    @Mapping(source = "radio.corporacion.descripcion", target = "radioCorporacion")
    ConsultaPlacaDTO toDto(ConsultaPlaca consultaPlaca);

    @Mapping(source = "radioIssi", target = "radio")
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
