package org.seguritech.cp.service.mapper;

import org.seguritech.cp.domain.*;
import org.seguritech.cp.service.dto.RadioDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Radio and its DTO RadioDTO.
 */
@Mapper(componentModel = "spring", uses = {MarcaMapper.class})
public interface RadioMapper extends EntityMapper<RadioDTO, Radio> {

    @Mapping(source = "marca.id", target = "marcaId")
    @Mapping(source = "marca.descripcion", target = "marcaDescripcion")
    RadioDTO toDto(Radio radio); 

    @Mapping(source = "marcaId", target = "marca")
    Radio toEntity(RadioDTO radioDTO);

    default Radio fromId(Long id) {
        if (id == null) {
            return null;
        }
        Radio radio = new Radio();
        radio.setId(id);
        return radio;
    }
}
