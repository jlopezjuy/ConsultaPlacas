package org.seguritech.cp.service.mapper;

import org.seguritech.cp.domain.*;
import org.seguritech.cp.service.dto.MarcaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Marca and its DTO MarcaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MarcaMapper extends EntityMapper<MarcaDTO, Marca> {

    

    

    default Marca fromId(Long id) {
        if (id == null) {
            return null;
        }
        Marca marca = new Marca();
        marca.setId(id);
        return marca;
    }
}
