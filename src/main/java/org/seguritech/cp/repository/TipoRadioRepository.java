package org.seguritech.cp.repository;

import org.seguritech.cp.domain.TipoRadio;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TipoRadio entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoRadioRepository extends JpaRepository<TipoRadio, Long> {

}
