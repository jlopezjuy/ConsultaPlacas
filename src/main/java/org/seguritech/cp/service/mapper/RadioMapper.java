package org.seguritech.cp.service.mapper;

import org.seguritech.cp.domain.*;
import org.seguritech.cp.service.dto.RadioDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Radio and its DTO RadioDTO.
 */
@Mapper(componentModel = "spring", uses = {MarcaMapper.class, MunicipioMapper.class, CorporacionMapper.class, TipoRadioMapper.class})
public interface RadioMapper extends EntityMapper<RadioDTO, Radio> {

    @Mapping(source = "marca.id", target = "marcaId")
    @Mapping(source = "marca.descripcion", target = "marcaDescripcion")
    @Mapping(source = "municipio.id", target = "municipioId")
    @Mapping(source = "municipio.descripcion", target = "municipioDescripcion")
    @Mapping(source = "corporacion.id", target = "corporacionId")
    @Mapping(source = "corporacion.descripcion", target = "corporacionDescripcion")
    @Mapping(source = "tipoRadio.id", target = "tipoRadioId")
    @Mapping(source = "tipoRadio.descripcion", target = "tipoRadioDescripcion")
    RadioDTO toDto(Radio radio); 

    @Mapping(source = "marcaId", target = "marca")
    @Mapping(source = "municipioId", target = "municipio")
    @Mapping(source = "corporacionId", target = "corporacion")
    @Mapping(source = "tipoRadioId", target = "tipoRadio")
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
