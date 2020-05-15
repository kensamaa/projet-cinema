package org.sid.GestionCinema.dao;

import org.sid.GestionCinema.entities.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
@RepositoryRestResource
public interface CategoryRepository extends JpaRepository<Categorie,Long>{

}
