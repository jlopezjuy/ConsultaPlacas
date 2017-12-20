package org.seguritech.cp.repository;

import org.seguritech.cp.domain.Radio;
import org.seguritech.cp.service.dto.RadioDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Radio entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RadioRepository extends JpaRepository<Radio, Long> {

}
