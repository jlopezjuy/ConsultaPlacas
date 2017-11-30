package org.seguritech.cp.repository;

import org.seguritech.cp.domain.Marca;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Marca entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MarcaRepository extends JpaRepository<Marca, Long> {

}
