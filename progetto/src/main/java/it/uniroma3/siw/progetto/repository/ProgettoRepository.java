package it.uniroma3.siw.progetto.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import it.uniroma3.siw.progetto.model.Progetto;

@Repository
public interface ProgettoRepository extends CrudRepository<Progetto, Long>
{

}
