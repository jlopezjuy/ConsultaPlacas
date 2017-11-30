package org.seguritech.cp.repository;

import org.seguritech.cp.domain.Corporacion;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Corporacion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CorporacionRepository extends JpaRepository<Corporacion, Long> {

}
